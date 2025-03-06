package samyorbot.cogs.SamyorClasses;

import java.util.Arrays;
import java.util.Random;

public class Character {
    private static double BASE_HEALTH = 15;
    private int transportSupplies = 5000;
    public Affiliation affiliation; // dynasty, company, circle, overlord
    public String backstory; // gonna be written into a json file
    private int discordID;
    private int birthDate;
    private int deathDate;
    private Country citizenship;
    private String name;
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

    public Character(int discordID, int birthDate, int deathDate, Country citizenship,
                     String name, int[] stats, double vitality) {
        this.discordID = discordID;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.citizenship = citizenship;
        this.name = name;

        for (int i = 0; i < 6; i++) {
            this.stats[i] = stats[i];
        }
        this.vitality = vitality;
        this.isAlive = this.vitality > 0 && Country.GLOBAL_YEAR <= deathDate;
    }

    public Character(int discordID, int birthDate, Country citizenship) {
        Random random = new Random();
        int deathDate = birthDate + random.nextInt(86) + 15; // base 15 years of life
        String name = this.randomNameGenerator(citizenship.getCulture());
        int[] stats = new int[6];
        Arrays.fill(stats, random.nextInt(10));
        new Character(discordID, birthDate, deathDate, citizenship, name, stats, BASE_HEALTH);
    }

    // for testing
    public Character() {
        this.name = "Samyor";
    }

    // for commanders or admirals or NPCs
    public Character(Culture culture) {
        Random random = new Random();
        String name = Culture.getCulturalName(culture);
        int deathDate = Country.GLOBAL_YEAR + random.nextInt(86) + 20;
        Arrays.fill(stats, random.nextInt(10));
    }

    // GET
    public int getDiscordID() {
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
        return name;
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
    public int getMaxWeight() {
        return transportSupplies;
    }

    // utilities
    public String randomNameGenerator(Culture culture) {
        String culturalName = culture.getCulturalName(culture);
        if (culturalName != null) {
            return culturalName;
        } else {
            culturalName = culture.getCulturalName(null);
            return culturalName;
        }
    }
}
