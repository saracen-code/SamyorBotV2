package com.java.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SheetsQuickstart {
    private static String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets from the resources directory
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /// WRITE
    public static void setValuesRange(Sheets service, String spreadsheetId, String range, List<List<Object>> data) throws IOException {
        // Create the ValueRange object to hold the data to be updated
        ValueRange body = new ValueRange()
                .setValues(data);

        // Create the update request
        Sheets.Spreadsheets.Values.Update updateRequest = service.spreadsheets().values()
                .update(spreadsheetId, range, body);

        // Set the value input option to 'RAW' or 'USER_ENTERED'
        updateRequest.setValueInputOption("RAW");

        // Execute the update
        updateRequest.execute();
        System.out.println("Rows updated successfully!");
    }

    /// READ
    public static List<List<Object>> getValueRange(Sheets service, String spreadsheetId, String range) throws IOException {
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();

        /// edge cases
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return Collections.emptyList();
        }
        return values;
    }

    /// UTILITIES

    public static int findFirstEmptyColumn(Sheets service, String spreadsheetId, String sheet, int row) throws IOException {
        String range = sheet + row + ":" + row;
        List<List<Object>> values = getValueRange(service, spreadsheetId, range); // Fetches the entire row
        List<Object> rowData = values.get(0); // First row (only one row is returned)

        // Iterate through the row to find the first empty column
        for (int i = 0; i < rowData.size(); i++) {
            if (rowData.get(i) == null || rowData.get(i).toString().trim().isEmpty()) {
                return i + 1;
            }
        }
        // If no empty column is found, return the next available column
        return rowData.size() + 1;
    }

    public static String columnNumberToLetter(int columnNumber) {
        StringBuilder columnLetter = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--; // Adjust for 1-based index
            columnLetter.insert(0, (char) ('A' + (columnNumber % 26)));
            columnNumber /= 26;
        }
        return columnLetter.toString();
    }

    public static List<Object> createObjectsFromColumns(List<List<Object>> data) {

        if (data == null || data.isEmpty()) return new ArrayList<>();

        int numColumns = data.get(0).size(); // Number of objects to create
        List<Object> objects = new ArrayList<>();

        for (int col = 0; col < numColumns; col++) {
            List<Object> columnData = new ArrayList<>();
            for (List<Object> row : data) {
                columnData.add(row.get(col)); // Get the column value from each row
            }
            objects.add(columnData);
        }

        return objects;
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1CcbI4dksDLtP-I5Kq7X6x_8WxMTWpOMT7rpYNW_l-wc";
        final String range = "CharacterList!A2:E";
        Sheets service = getSheetsService();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s, %s\n", row.get(0), row.get(4));
            }
        }
    }
}