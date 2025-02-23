

/*
package SamyorClasses;
public class Main {
    public static void main(String[] args) {
        // initialize Culture, Diplomacy, Domain, Military, Province, Cities, Characters and Countries Last

        
    }
} */


package SamyorClasses;

import java.util.*;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create cities
        City city1 = new City("Kingstown", true);  // City with a port
        City city2 = new City("Fortville", false); // City without a port

        // Create a list of cities for the domain
        List<City> cityList = new ArrayList<>();
        cityList.add(city1);
        cityList.add(city2);

        // Assuming a King character (for example purposes)
        Character king = new King();

        // Create the domain with the king and the cities
        Domain domain = new Domain(king, cityList);

        // Create some buildings
        Building road = new Building("Road", BuildingType.INFRASTRUCTURE, 300, "None", "Cheaper transportation", false);
        Building port = new Building("Port", BuildingType.INFRASTRUCTURE, 1000, "None", "Local sea trade", false);

        // Try adding buildings to the cities
        domain.addBuilding(road, city1); // Should succeed
        domain.addBuilding(port, city2); // Should fail (no port in city)
    }
}
