package com.java.classes;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Country {
    public static int GLOBAL_YEAR;
    public static String sheetId = "927499955";

    // Basics
    private int columnIndex;
    private String countryname; // row 1
    private String successionType = "Dynastic"; // row 2
    /*
     * "Dynastic", "Elective"
     */

    // Mechanics
    private int population; // row 3
    private double[] stats = new double[15];
    /*
     * 4: growthRate [0]
     * 5: popCapacity [1]
     * 6: mainMarket [2]
     * 7: currency [3]
     * 8: budget [4]
     * -- classes --
     * 9: administration [5]
     * 10: nobility [6]
     * 11: institutions [7]
     * 12: landowners [8]
     * 13: burghers [9]
     * 14: peasants [10]
     * 15: tribes [11]
     * 16: bondmen [12]
     * 17: devastation [13]
     * 18: centralization [14]
     */
    // Politics
    private City capital;
    private ProvinceList provinceList;
    private King king;
    // provincelist


    // Spheres
    private Diplomacy diplomacy;
    private Culture culture;
    private Military military;
    private Taxes taxes;
    private Domain domain;



    public Country(String countryname, String successionType, int population,
                   City capital, ProvinceList provinceList, King king, Diplomacy diplomacy,
                   Culture culture, Military military, Taxes taxes, Domain domain) {
        this.countryname = countryname;
        this.successionType = successionType;
        this.population = population;
        this.capital = capital;
        this.provinceList = provinceList;
        this.king = king;
        this.diplomacy = diplomacy;
        this.culture = culture;
        this.military = military;
        this.taxes = taxes;
        this.domain = domain;
    }
    // ----------------------------------GET
    public String getName() {
        return this.countryname;
    }
    // Basics
    public String getCountryname() {
        return countryname;
    }
    public String getSuccessionType() {
        return successionType;
    }

    // Politics
    public int getPopulation() {
        return population;
    }
    public City getCapital() {
        return capital;
    }
    public ProvinceList getProvince() {
        return provinceList;
    }
    public King getKing() {
        return king;
    }

    // Spheres
    public Diplomacy getDiplomacy() {
        return diplomacy;
    }
    public Culture getCulture() {
        return culture;
    }
    public Military getMilitary() {
        return military;
    }
    public Taxes getTaxes() {
        return taxes;
    }
    public Domain getDomain() {
        return domain;
    }

    // edit
    public void changeSuccession(String successionType) {
        if (successionType.equals("Dynastic") || successionType.equals("Elective")) {
            this.successionType = successionType;
        } else {
            throw new IllegalArgumentException("Not a valid successionType");
        }
    }

    /*

    // Static initializer method to initialize country fields from the Google Sheet
    public void initializer() throws IOException, GeneralSecurityException {
        GoogleSheetsService googleSheetsService = new GoogleSheetsService(
                new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        GoogleSheetsService.getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                        .setApplicationName("Initializer").build()
        );
        Sheets sheetsService = googleSheetsService.sheetsService;  // Get the Sheets service
        GoogleSheetsCommands googleSheetsCommands = new GoogleSheetsCommands(sheetsService);

        // Search the first row for the country name
        ValueRange headerResponse = googleSheetsCommands.getHeaderRow(String.valueOf(sheetId));  // Using getHeaderRow method to fetch first row
        List<List<Object>> headerValues = headerResponse.getValues();

        if (headerValues == null || headerValues.isEmpty()) {
            throw new IOException("No data found in the first row.");
        }

        // Find the column index for the country name
        int countryColumnIndex = -1;
        for (int i = 0; i < headerValues.get(0).size(); i++) {
            if (headerValues.get(0).get(i).toString().equalsIgnoreCase(this.countryname)) {
                countryColumnIndex = i;
                break;
            }
        }

        if (countryColumnIndex == -1) {
            throw new IOException("Country name not found in the sheet.");
        }

        // Now, use this column index to get all the data for the country from subsequent rows
        ValueRange countryData = googleSheetsCommands.getCountryData(sheetId, countryColumnIndex);  // Using getCountryData method to fetch country data
        List<List<Object>> countryValues = countryData.getValues();

        // Ensure we have at least 18 rows, otherwise fill missing values with defaults
        for (int i = 0; i < 18; i++) {
            String value = (countryValues != null && i < countryValues.size() && !countryValues.get(i).isEmpty())
                    ? countryValues.get(i).get(0).toString()
                    : "";  // Default to empty string if missing

            // Assign values based on index (you can modify these assignments as needed)
            switch (i) {
                case 0 -> this.countryname = value;
                case 1 -> this.successionType = value;
                case 2 -> this.population = value.isEmpty() ? 0 : Integer.parseInt(value);
                case 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 ->
                        this.stats[i - 3] = value.isEmpty() ? 0 : Double.parseDouble(value);  // Map index 3-18 to stats[0]-stats[15]
            }
        }

    }

     */


}
