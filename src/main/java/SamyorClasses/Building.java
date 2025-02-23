package SamyorClasses;

import java.util.List;
import java.util.ArrayList;

// Class representing a Building
class Building {
    private String name;
    private BuildingType type;
    private int cost;
    private String restriction;
    private String production;
    private boolean kingOnly;

    public Building(String name, BuildingType type, int cost, String restriction, String production, boolean kingOnly) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.restriction = restriction;
        this.production = production;
        this.kingOnly = kingOnly;
    }

    public String getName() {
        return name;
    }

    public BuildingType getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public String getRestriction() {
        return restriction;
    }

    public String getProduction() {
        return production;
    }

    public boolean isKingOnly() {
        return kingOnly;
    }
}

// Enum for different building types
enum BuildingType {
    INFRASTRUCTURE, MILITARY, ECONOMIC, GOVERNMENT, RELIGIOUS, EDUCATION, HEALTH, HOUSING, SPECIAL
}

// Enum for Medieval Buildings with Levels
enum MedievalBuilding {
    // Infrastructure
    ROAD_LEVEL_1("Road", BuildingType.INFRASTRUCTURE, 300, "None", "Cheaper transportation", false),
    ROAD_LEVEL_2("Paved Road", BuildingType.INFRASTRUCTURE, 800, "None", "Cheaper transportation", false),
    PORT_LEVEL_1("Anchorage", BuildingType.INFRASTRUCTURE, 1000, "None", "Local sea trade", false),
    PORT_LEVEL_2("Harbor", BuildingType.INFRASTRUCTURE, 2500, "None", "International Trade", false),
    WAREHOUSE_LEVEL_1("Warehouse", BuildingType.INFRASTRUCTURE, 1000, "None", "Cheaper transportation, more merchants", false),
    WAREHOUSE_LEVEL_2("Extended Warehouse", BuildingType.INFRASTRUCTURE, 2500, "none", "Cheaper transportation, more merchants", false),

    // Military
    BARRACKS("Barracks", BuildingType.MILITARY, 1000, "None", "Train Soldiers, more manpower", false),
    SIEGE_WORKSHOP_LEVEL_1("Siege Workshop", BuildingType.MILITARY, 1500, "None", "Craft Basic Siege Weapons", true),
    SIEGE_WORKSHOP_LEVEL_2("Advanced Siege Workshop", BuildingType.MILITARY, 4000, "None", "Craft Powerful Siege Weapons", true),
    FORT_LEVEL_1("Basic Ramparts", BuildingType.MILITARY, 4000, "None", "Basic fort for defense.", true),
    FORT_LEVEL_2("Advanced City Defenses", BuildingType.MILITARY, 4000, "None", "Stronghold with advanced fortifications.", true),

    // Economic
    MARKETPLACE_LEVEL_1("Marketplace", BuildingType.ECONOMIC, 500, "None", "Increased Trade", false),
    MARKETPLACE_LEVEL_2("Market Offices", BuildingType.ECONOMIC, 1500, "Capital City Only", "Enables Customs Control", true),
    LUMBERYARD_LEVEL_1("Lumberyard", BuildingType.ECONOMIC, 1500, "None", "Basic timber production", true),
    LUMBERYARD_LEVEL_2("Lumberyard", BuildingType.ECONOMIC, 1500, "None", "Advanced lumberyard with better-quality wood", true),
    MINE_LEVEL_1("Mine", BuildingType.ECONOMIC, 1500, "None", "Simple mine for extracting basic ores", true),
    MINE_LEVEL_2("Mine", BuildingType.ECONOMIC, 1500, "None", "Deep mine with access to rare minerals", true),

    // Government
    TOWN_HALL_LEVEL_1("Town Hall", BuildingType.GOVERNMENT, 1000, "None", "More efficient tax collection", false);

    private final String name;
    private final BuildingType type;
    private final int cost;
    private final String restriction;
    private final String production;
    private final boolean kingOnly;

    // Constructor for each building
    MedievalBuilding(String name, BuildingType type, int cost, String restriction, String production, boolean kingOnly) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.restriction = restriction;
        this.production = production;
        this.kingOnly = kingOnly;
    }

    public String getName() {
        return name;
    }

    public BuildingType getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public String getRestriction() {
        return restriction;
    }

    public String getProduction() {
        return production;
    }

    public boolean isKingOnly() {
        return kingOnly;
    }

    // Static method to return a list of all medieval buildings
    public static List<MedievalBuilding> getAllBuildings() {
        List<MedievalBuilding> buildings = new ArrayList<>();
        for (MedievalBuilding building : MedievalBuilding.values()) {
            buildings.add(building);
        }
        return buildings;
    }
}
