package SamyorClasses;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class City {
    public String name; // Public field for direct access
    private Country country;
    private boolean hasPort = false; // by default
    private ArrayList<Building> buildingsInCity = new ArrayList<Building>();

    public City(Country country, String cityName, boolean hasPort) {
        this.name = cityName;
        this.country = country;
    }

    public City(String cityName, boolean hasPort) {
        this.name = cityName;
        this.hasPort = hasPort;
    }

    public String getName() {
        return name;
    }

    public boolean hasPort() {
        return !hasPort;
    }

    public List<Building> getBuildings() {
        return buildingsInCity;
    }

    @Override
    public String toString() {
        return name + (hasPort ? " (Port)" : "");
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}