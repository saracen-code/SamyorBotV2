package samyorbot.cogs.SamyorClasses;

import java.util.List;

// Domain class managing buildings
class Domain {
    private Character character;
    private boolean isKingDomain = character instanceof King;
    private List<City> cityList;

    public Domain(Character character) {
        this.character = character;
    }
    public Domain(Character character, List<City> cityList) {
        this.character = character;
        this.cityList = cityList;
    }

    // Method to check if a city is in the list of cities in this domain
    public boolean isCityInDomain(City city) {
        return cityList.contains(city);
    }

    public boolean addBuilding(Building building, City city) {
        if (!isCityInDomain(city)) {
            System.out.println("City not found in the domain!");
            return false;
        }
        if (building.isKingOnly() && !isKingDomain) {
            System.out.println("Only the King can build: " + building.getName());
            return false;
        }
        if (building.getRestriction().equals("Port Required") && !city.hasPort()) {
            System.out.println("Cannot build " + building.getName() + " - Requires a port!");
            return false;
        }
        city.getBuildings().add(building);
        System.out.println(building.getName() + " constructed!");
        return true;
    }

    public void displayBuildings(City city) {
        if (!isCityInDomain(city)) {
            System.out.println("City not found in the domain!");
            return;
        }
        if (city.getBuildings().isEmpty()) {
            System.out.println("No buildings in this domain.");
            return;
        }
        for (Building b : city.getBuildings()) {
            System.out.println("- " + b.getName() + " (" + b.getType() + ") - Cost: " + b.getCost());
        }
    }
}


