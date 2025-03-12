package com.java.utils;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class SheetsCommands {
/*
    private static Sheets sheetsService;
    private static String spreadsheetId = "1CcbI4dksDLtP-I5Kq7X6x_8WxMTWpOMT7rpYNW_l-wc";

    public SheetsCommands(Sheets sheetsService, String spreadsheetId) {
        SheetsCommands.sheetsService = sheetsService;
        SheetsCommands.spreadsheetId = spreadsheetId;
    }

    // Read data from a given range
    public static ValueRange readDataFromRange(String range) throws IOException {
        return sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
    }

    // Write data to a given range
    public static UpdateValuesResponse writeDataToRange(String range, List<List<Object>> values) throws IOException {
        ValueRange body = new ValueRange().setValues(values);
        return sheetsService.spreadsheets().values()
                .update(spreadsheetId, range, body)
                .setValueInputOption("RAW")  // You can use "USER_ENTERED" if you want the data to be interpreted (formulas, dates, etc.)
                .execute();
    }

    // Append data to a given range
    public static void appendDataToRange(String range, List<List<Object>> values) throws IOException {
        ValueRange body = new ValueRange().setValues(values);
        sheetsService.spreadsheets().values()
                .append(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .setInsertDataOption("INSERT_ROWS")
                .execute();
    }

    // Example usage of readDataFromRange and writeDataToRange
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        // Use SheetsQuickStart to get the Sheets service
        Sheets sheetsService = SheetsQuickstart.getSheetsService();
        String spreadsheetId = "1CcbI4dksDLtP-I5Kq7X6x_8WxMTWpOMT7rpYNW_l-wc";  // Replace with your spreadsheet ID

        SheetsCommands sheetsCommands = new SheetsCommands(sheetsService, spreadsheetId);

        // Read data
        String range = "Sheet1!A1:D10";
        ValueRange result = SheetsCommands.readDataFromRange(range);
        System.out.println("Data read from range: " + result.getValues());

        // Write data
        List<List<Object>> newValues = List.of(
                List.of("A1", "B1", "C1", "D1"),
                List.of("A2", "B2", "C2", "D2")
        );
        SheetsCommands.writeDataToRange("Sheet1!A1:D2", newValues);

        // Append data
        List<List<Object>> appendedValues = List.of(
                List.of("A3", "B3", "C3", "D3")
        );
        SheetsCommands.appendDataToRange("Sheet1!A1:D10", appendedValues);
    }

 */
}
