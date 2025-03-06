package samyorbot.cogs.SamyorClasses;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class City extends WeightedWeb {
    public int development;
    public String saskName;
    public String name; // Public field for direct access
    private Country country;
    private boolean hasPort = false; // by default
    private ArrayList<Building> buildingsInCity = new ArrayList<Building>();

    public City(Country country, String cityName, String saskName, boolean hasPort) {
        this.saskName = saskName;
        this.name = cityName;
        this.country = country;
    }

    public City(String cityName, String saskName, int dev, boolean hasPort) {
        this.name = cityName;
        this.saskName = saskName;
        this.hasPort = hasPort;
        this.development = dev;
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

    public static void main(String[] args) {
        City Yishi = new City("Yishi", "Yitesh", 14, false);
        City Lucit = new City("Lucit", "Lujit", 2, false);
        City Anwan = new City("Anwan", "Nînewan", 4, false);
        City Disen = new City("Disen", "Disēn", 2, false);
        City Iwej = new City("Iwej", "Iwedj", 3, false);
        City Shegaen = new City("Shegaen", "Shegînhaeen", 5, false);
        City Diansheg = new City("Di'ansheg", "Dî'anshîneg", 7, false);
        City Haseba = new City("Haseba", "Ehsîbba", 8, false);
        City Halagua = new City("Halagua", "Ehregeg", 5, false);
        City Shenem = new City("Shenem", "Shenem", 2, false);
        City Rageg = new City("Rageg", "Ra'ugeg", 2, false);

        Yishi.addLandConnection(Yishi, Lucit, 300);
        Yishi.addLandConnection(Yishi, Anwan, 300);
        Yishi.addLandConnection(Yishi, Iwej, 300);
        Yishi.addLandConnection(Yishi, Disen, 300);
        Yishi.addLandConnection(Yishi, Shenem, 300);
        Yishi.addLandConnection(Yishi, Halagua, 300);
        Yishi.addLandConnection(Yishi, Lucit, 300);
        Yishi.addLandConnection(Yishi, Shegaen, 300);
    }
}