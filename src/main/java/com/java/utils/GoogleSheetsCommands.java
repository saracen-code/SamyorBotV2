package com.java.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.java.utils.GoogleSheetsService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleSheetsCommands {

    private Sheets sheetsService;

    // Constructor to initialize the Sheets service
    public GoogleSheetsCommands(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }

    // ---UTILITIES
    private String convertIndexToColumnLetter(int columnIndex) {
        StringBuilder columnLetter = new StringBuilder();
        while (columnIndex >= 0) {
            columnLetter.insert(0, (char) ('A' + (columnIndex % 26)));
            columnIndex = (columnIndex / 26) - 1;
        }
        return columnLetter.toString();
    }

    // ---COMMANDS
    public ValueRange getCountryData(String sheetId, int columnIndex) throws IOException {
        // Convert numeric column index to letter (A, B, C, ...)
        String columnLetter = convertIndexToColumnLetter(columnIndex);

        // Construct the range (e.g., "B1:B" to fetch the entire column)
        String range = columnLetter + "1:" + columnLetter + "18";

        // Read the column data from the Google Sheet
        return sheetsService.spreadsheets().values()
                .get(sheetId, range)
                .execute();
    }

    // Method to fetch the first row (header row) of the sheet
    public ValueRange getHeaderRow(String sheetId) throws IOException {
        String range = "A1:Z1";  // Range for the first row (adjust as needed for your sheet)

        // Fetch the values for the first row (header)
        ValueRange response = sheetsService.spreadsheets().values()
                .get(sheetId, range)  // Use the sheetId and range to fetch the first row
                .execute();

        return response;  // Return the header row data
    }

    // Function to create the appendDimension request with dynamic values and sheetId as input
    public Request createAppendDimensionRequest(String sheetId, String dimension, int length) {
        AppendDimensionRequest appendRequest = new AppendDimensionRequest()
                .setSheetId(Integer.parseInt(sheetId))
                .setDimension(dimension)
                .setLength(length);

        return new Request().setAppendDimension(appendRequest);
    }

    // Function to create the deleteDimension request with dynamic values and sheetId as input
    public Request createDeleteDimensionRequest(String sheetId, String dimension, int startIndex, int endIndex) {
        DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
                .setRange(new DimensionRange()
                        .setSheetId(Integer.parseInt(sheetId))
                        .setDimension(dimension)
                        .setStartIndex(startIndex)
                        .setEndIndex(endIndex));

        return new Request().setDeleteDimension(deleteRequest);
    }

    // Function to create the insertDimension request with dynamic values and sheetId as input
    public Request createInsertDimensionRequest(String sheetId, String dimension, int startIndex, int endIndex, boolean inheritFromBefore) {
        InsertDimensionRequest insertRequest = new InsertDimensionRequest()
                .setRange(new DimensionRange()
                        .setSheetId(Integer.parseInt(sheetId))
                        .setDimension(dimension)
                        .setStartIndex(startIndex)
                        .setEndIndex(endIndex))
                .setInheritFromBefore(inheritFromBefore);

        return new Request().setInsertDimension(insertRequest);
    }

    // Function to add an array of values into a specific column with sheetId as input
    public Request createInsertArrayToColumnRequest(String sheetId, int columnIndex, List<List<Object>> values) {
        ValueRange body = new ValueRange()
                .setRange("Sheet1!A1")  // You can specify the range more dynamically based on the column and row
                .setValues(values);

        return new Request().setUpdateCells(new UpdateCellsRequest()
                .setRange(new GridRange()
                        .setSheetId(Integer.parseInt(sheetId))
                        .setStartColumnIndex(columnIndex)
                        .setEndColumnIndex(columnIndex + values.get(0).size())
                        .setStartRowIndex(0)  // Assuming you want to start from the first row
                        .setEndRowIndex(values.size()))  // Set this based on the size of your values
                .setFields("userEnteredValue")
                .setRows(values.stream()
                        .map(val -> new RowData().setValues(val.stream()
                                .map(data -> new CellData().setUserEnteredValue(new ExtendedValue().setStringValue((String) data)))
                                .collect(Collectors.toList())))
                        .collect(Collectors.toList()))
        );
    }

    // New Function to add an array of values across multiple columns with sheetId as input
    public Request createInsertArrayToMultipleColumnsRequest(String sheetId, String range, List<List<Object>> values) {
        ValueRange body = new ValueRange()
                .setRange(range)  // Specify your range here like "Sheet1!A1:C5"
                .setValues(values);

        return new Request().setUpdateCells(new UpdateCellsRequest()
                .setRange(new GridRange()
                        .setSheetId(Integer.parseInt(sheetId))
                        .setStartRowIndex(0)
                        .setEndRowIndex(values.size())
                        .setStartColumnIndex(0)
                        .setEndColumnIndex(values.get(0).size()))
                .setFields("userEnteredValue")
                .setRows(values.stream()
                        .map(row -> new RowData().setValues(row.stream()
                                .map(val -> new CellData().setUserEnteredValue(new ExtendedValue().setStringValue((String) val)))
                                .collect(Collectors.toList())))
                        .collect(Collectors.toList()))
        );
    }

    // Function to create a request to update cells with a formula (landTaxAssessment) with sheetId as input
    public Request createLandTaxAssessmentRequest(String sheetId, int startRowIndex, int endRowIndex, int startColumnIndex, int endColumnIndex) {
        GridRange range = new GridRange()
                .setSheetId(Integer.parseInt(sheetId))
                .setStartRowIndex(startRowIndex)
                .setEndRowIndex(endRowIndex)
                .setStartColumnIndex(startColumnIndex)
                .setEndColumnIndex(endColumnIndex);

        // Creating a formula to be inserted into the cells
        CellData cellData = new CellData()
                .setUserEnteredValue(new ExtendedValue().setFormulaValue("=ABS(landTaxComputation(\"GhallabMarket\"))"));

        RowData rowData = new RowData().setValues(Arrays.asList(cellData));
        UpdateCellsRequest updateCellsRequest = new UpdateCellsRequest()
                .setRange(range)
                .setRows(Arrays.asList(rowData))
                .setFields("userEnteredValue");

        return new Request().setUpdateCells(updateCellsRequest);
    }

    // Example of using these functions to build the requests dynamically
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // Create the Sheets service with authentication from GoogleSheetsService
        GoogleSheetsService googleSheetsService = new GoogleSheetsService(
                new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        GoogleSheetsService.getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                        .setApplicationName("Google Sheets API Example").build()
        );
        Sheets sheetsService = googleSheetsService.sheetsService;  // Get the Sheets service

        // Create an instance of GoogleSheetsCommands to use its methods
        GoogleSheetsCommands googleSheetsCommands = new GoogleSheetsCommands(sheetsService);

        String sheetId = "1CcbI4dksDLtP-I5Kq7X6x_8WxMTWpOMT7rpYNW_l-wc";  // Provide your actual sheetId

        int length = 5;   // For append requests
        int startIndex = 1;  // Replace with your values
        int endIndex = 3;    // Replace with your values
        int columnIndex = 0;  // Example column (0 represents column A)

        // Example array to insert into a column (column A)
        List<List<Object>> valuesToInsertColumn = Arrays.asList(
                Arrays.asList("Value 1"),
                Arrays.asList("Value 2"),
                Arrays.asList("Value 3")
        );

        // Example array to insert into multiple columns (columns A to C)
        List<List<Object>> valuesToInsertMultiColumns = Arrays.asList(
                Arrays.asList("Value 1", "Value 2", "Value 3"),
                Arrays.asList("Value 4", "Value 5", "Value 6")
        );

        // Create requests dynamically
        List<Request> requests = Arrays.asList(
                googleSheetsCommands.createAppendDimensionRequest(sheetId, "ROWS", length),
                googleSheetsCommands.createDeleteDimensionRequest(sheetId, "COLUMNS", startIndex, endIndex),
                googleSheetsCommands.createInsertDimensionRequest(sheetId, "ROWS", startIndex, endIndex, true),
                googleSheetsCommands.createInsertArrayToColumnRequest(sheetId, columnIndex, valuesToInsertColumn),
                googleSheetsCommands.createInsertArrayToMultipleColumnsRequest(sheetId, "Sheet1!A1:C2", valuesToInsertMultiColumns),
                googleSheetsCommands.createLandTaxAssessmentRequest(sheetId, 0, 1, 1, 2)  // Adding landTax formula to range (e.g., A1:B1)
        );

        // Execute the batch update
        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        sheetsService.spreadsheets().batchUpdate(sheetId, batchUpdateRequest).execute();
    }
}
