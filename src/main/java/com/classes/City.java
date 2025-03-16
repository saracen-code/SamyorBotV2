package com.classes;

public class City extends WeightedWeb {
    private String name;
    private String saskName;
    private int development;
    private boolean hasPort;

    public City(String name, String saskName, int development, boolean hasPort) {
        super();  // Initialize WeightedWeb
        this.name = name;
        this.saskName = saskName;
        this.development = development;
        this.hasPort = hasPort;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSaskName() {
        return saskName;
    }

    public int getDevelopment() {
        return development;
    }

    public boolean hasPort() {
        return hasPort;
    }

    // Override toString to display a nice city representation
    @Override
    public String toString() {
        return name + (hasPort ? " (Port)" : "");
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