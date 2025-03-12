package com.java.classes;

import com.google.api.services.sheets.v4.Sheets;
import com.java.utils.SheetsQuickstart;
import org.apache.http.annotation.Contract;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import static com.java.utils.SheetsQuickstart.*;

public class Player {
    private static List<Player> characters = new ArrayList<>();
    private static Set<Integer> generatedIDs = new HashSet<>();

    ///  CONSTANTS
    public static final String SPREADSHEET_ID = "1CcbI4dksDLtP-I5Kq7X6x_8WxMTWpOMT7rpYNW_l-wc";
    public static final String SHEET = "CharacterList!";
    private static double BASE_HEALTH = 15;

    /// PLAYER DATA

    private int ID;
    private String charName;
    private int fertility = 2;
    private double personalFunds = 5000;
    public Affiliation affiliation; // dynasty, company, circle, overlord
    public String backstory; // gonna be written into a json file
    private long discordID;
    private int birthDate;
    private int deathDate;
    private Country citizenship;
    private int investmentReputation;
    private int[] stats = new int[6];
    /*
     * 0: duelStrength
     * 1: azhiStrength
     * 2: militaryPoint
     * 3: Persuasion
     * 4: stewardshipPoint
     * 5: wisdomPoint
     */
    private String imageURL;
    private double vitality;
    private boolean isAlive;

    public Player(int id, String charName, String type, int fertility, int personalFunds, int reputation, long discordID,
                  int birthDate, int deathDate, int duelStrength, int azhiStrength, int militaryPoint, int persuasion,
                  int stewardshipPoint, int wisdomPoint, int vitality, boolean isAlive) {
        this.ID = ID;
        this.charName = charName;
        this.investmentReputation = reputation;
        this.fertility = fertility;
        this.personalFunds = personalFunds;
        this.investmentReputation = investmentReputation;
        this.discordID = discordID;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.stats[0] = duelStrength;
        this.stats[1] = azhiStrength;
        this.stats[2] = militaryPoint;
        this.stats[3] = persuasion;
        this.stats[4] = stewardshipPoint;
        this.stats[5] = wisdomPoint;
        this.vitality = vitality;
        this.isAlive = isAlive;
    }

    public Player() {

    }

    ///  CONSTRUCTORS
    public void Player() {
        Random random = new Random();
        int newID;

        // Keep generating until a unique ID is found
        do {
            newID = random.nextInt(1000000);  // Generate a random ID (limit to 1,000,000)
        } while (generatedIDs.contains(newID));  // Check if the ID already exists

        generatedIDs.add(newID);  // Add the ID to the set to prevent duplicates
        this.ID = newID;
    }

    ///  UTILITIES
    public String toString() {
        return charName + " born in " + birthDate + " | " + personalFunds + " dreyîm";
    }

    public String randomNameGenerator(Culture culture) {
        String culturalName = culture.getCulturalName(culture);
        if (culturalName != null) {
            return culturalName;
        } else {
            culturalName = culture.getCulturalName(null);
            return culturalName;
        }
    }

    ///  GETTERS
    public static List<Player> getCharacters() {
        return characters;
    }
    public int getID() {return this.ID; }
    public int getFertility() { return this.fertility; }
    public long getDiscordID() {
        return this.discordID;
    }
    public int getBirthDate() {
        return birthDate;
    }
    public int getDeathDate() {
        return deathDate;
    }
    public Country getCitizenship() {
        return citizenship;
    }
    public String getName() {
        return charName;
    }
    public int getDuelStrength() {
        return stats[0];
    }
    public int getAzhiStrength() {
        return stats[1];
    }
    public int getMilitaryPoint() {
        return stats[2];
    }
    public int getDiplomacyPoint() {
        return stats[3];
    }
    public int getStewardshipPoint() {
        return stats[4];
    }
    public int getWisdomPoint() {
        return stats[5];
    }
    public int[] getStats() {
        return stats;
    }
    public double getMaxWeight() {
        return personalFunds;
    }

    ///  SETTERS
    public static void setCharacters(List<Player> characters) {
        Player.characters = characters;
    }
    public void setNewId() {
        Random random = new Random();
        int newID;

        // Keep generating until a unique ID is found
        do {
            newID = random.nextInt(1000000);  // Generate a random ID (limit to 1,000,000)
        } while (generatedIDs.contains(newID));  // Check if the ID already exists

        generatedIDs.add(newID);  // Add the ID to the set to prevent duplicates
        this.ID = newID;
    }
    public void setCharName(String charName) {
        this.charName = charName;
    }
    public void setFertility(int fertility) {
        this.fertility = fertility;
    }
    public void setDiscordID(long discordID) {
        this.discordID = discordID;
    }
    public void setBirthDate(int year) {
        this.birthDate = year;
    }
    public void setNewDeathDate(int year) {
        Random random = new Random();
        this.deathDate = random.nextInt(90) + 12;
    }
    public void setStats(int key, int value) {
        stats[key] = value;
    }
    public void setStats(int key) {
        Random random = new Random();
        int value = random.nextInt(12);
        stats[key] = value;
    }
    public void setPersonalFunds(int personalFunds) {
        this.personalFunds = personalFunds;
    }
    public void changePersonalFunds(int variation) {
        this.personalFunds += variation;
    }
    public void setVitality(int vitality) {
        this.vitality = vitality;
    }
    public void setAlive() {
        if (vitality > 0 && deathDate > Country.GLOBAL_YEAR) {
            this.isAlive = true;
        } else {
            this.isAlive = false;
            System.out.println("Error: Cannot bring back to life a character without first changing its vitality and death date.");
        }
    }
    public void setDead() {
        this.vitality = 0;
        this.deathDate = Country.GLOBAL_YEAR;
        this.isAlive = false;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public void changeInvestmentReputation(int variation) {
        this.investmentReputation += variation;
    }

    /// SAVER
    public void charSave() throws IOException, GeneralSecurityException {
        Sheets service = SheetsQuickstart.getSheetsService();

        // find the column where to save
        int lastColumn = findFirstEmptyColumn(service, SPREADSHEET_ID, SHEET, 1);
        String lastColumnLetter = columnNumberToLetter(lastColumn);

        // setup our data in correct format

        List<List<Object>> data;
        String type;
        if (this instanceof King) {
            type = "King";
            data = Collections.singletonList(Arrays.asList(this.ID, this.charName, type, this.fertility, ((King)this).successorID,
                    this.personalFunds, this.investmentReputation, this.discordID, this.birthDate, this.deathDate,
                    this.stats[0], this.stats[1], this.stats[2], this.stats[3], this.stats[4], this.stats[5],
                    this.vitality, this.isAlive));

        } else {
            type = "Commoner";
            data = Collections.singletonList(Arrays.asList(this.ID, this.charName, type, this.fertility, 0, // successor defaulted to "0"
                    this.personalFunds, this.investmentReputation, this.discordID, this.birthDate, this.deathDate,
                    this.stats[0], this.stats[1], this.stats[2], this.stats[3], this.stats[4], this.stats[5],
                    this.vitality, this.isAlive));
        }
        setValuesRange(service, SPREADSHEET_ID, SHEET + lastColumnLetter + "1:18", data);
    }

    /// INITIALIZER
    public static void charInitializer() throws GeneralSecurityException, IOException {
        Sheets service = SheetsQuickstart.getSheetsService();
        int lastColumn = findFirstEmptyColumn(service, SPREADSHEET_ID, SHEET, 1);
        String lastColumnLetter = columnNumberToLetter(lastColumn);

        // Fetch data from B1 to lastColumnLetter18
        List<List<Object>> characterList = getValueRange(service, SPREADSHEET_ID, SHEET + "B1:" + lastColumnLetter + "18");
        List<Object> characterColumns = createObjectsFromColumns(characterList);

        for (Object column : characterColumns) {
            if (!(column instanceof List)) {
                System.err.println("Error: Expected a list but found " + column.getClass());
                continue;
            }

            List<Object> row = (List<Object>) column;  // ✅ Casting to List<Object>

            String type = safeToString(row.get(2));
            Player character;

            if (type.equalsIgnoreCase("King")) {
                character = new King();
                System.out.println("King created");
            } else if (type.equalsIgnoreCase("Adventurer")) {
                character = new Player();
                System.out.println("Adventurer created");
            } else {
                System.err.println("Unrecognized character type: " + type);
                return;
            }

            character.ID = safeToInt(row.get(0));
            character.charName = safeToString(row.get(1));
            character.fertility = safeToInt(row.get(3));

            if (character instanceof King) {
                ((King) character).successorID = safeToInt(row.get(4));
            }

            character.personalFunds = safeToInt(row.get(5));
            character.investmentReputation = safeToInt(row.get(6));
            character.discordID = safeToLong(row.get(7));
            character.birthDate = safeToInt(row.get(8));
            character.deathDate = safeToInt(row.get(9));

            for (int i = 0; i < 6; i++) {
                character.stats[i] = safeToInt(row.get(i + 10));  // ✅ Ensure correct index range
            }

            character.vitality = safeToInt(row.get(16));
            character.isAlive = !safeToString(row.get(17)).trim().equals("0");

            characters.add(character);
        }
    }
    // Helper method to safely convert an Object to int
    private static int safeToInt(Object value) {
        if (value == null || value.toString().trim().isEmpty()) {
            return 0; // Default value for null or empty cells
        }
        try {
            if (value instanceof Number) {
                return ((Number) value).intValue(); // Handle Double, Integer, etc.
            } else {
                return Integer.parseInt(value.toString()); // Parse from String
            }
        } catch (NumberFormatException e) {
            System.err.println("Error converting to int: " + value);
            return 0; // Default value for invalid numbers
        }
    }

    // Helper method to safely convert an Object to double
    private static double safeToDouble(Object value) {
        if (value == null || value.toString().trim().isEmpty()) {
            return 0.0; // Default value for null or empty cells
        }
        try {
            if (value instanceof Number) {
                return ((Number) value).doubleValue(); // Handle Double, Integer, etc.
            } else {
                return Double.parseDouble(value.toString()); // Parse from String
            }
        } catch (NumberFormatException e) {
            System.err.println("Error converting to double: " + value);
            return 0.0; // Default value for invalid numbers
        }
    }

    // Helper method to safely convert an Object to long
    private static long safeToLong(Object value) {
        if (value == null || value.toString().trim().isEmpty()) {
            return 0L; // Default value for null or empty cells
        }
        try {
            if (value instanceof Number) {
                return ((Number) value).longValue(); // Handle Double, Integer, etc.
            } else {
                return Long.parseLong(value.toString()); // Parse from String
            }
        } catch (NumberFormatException e) {
            System.err.println("Error converting to long: " + value);
            return 0L; // Default value for invalid numbers
        }
    }

    // Helper method to safely convert an Object to String
    private static String safeToString(Object value) {
        if (value == null) {
            return ""; // Default value for null cells
        }
        return value.toString().trim(); // Trim whitespace
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        Player.charInitializer();

        for (Player c : characters ) {
            System.out.println(c);
        }
    }
}

