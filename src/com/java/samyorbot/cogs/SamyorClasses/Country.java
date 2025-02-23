package samyorbot.cogs.SamyorClasses;

public class Country {
    public static int GLOBAL_YEAR;

    // Basics
    private String countryname;
    private String successionType = "Dynastic";
    /*
     * "Dynastic", "Elective"
     */

    // State Finances

    // Politics
    private int population;
    private City capital;
    private ProvinceList provinceList;
    private King king;

    // Spheres
    private Diplomacy diplomacy;
    private Culture culture;
    private Military military;
    private Taxes taxes;
    private Domain domain;

    // Mechanics
    private double devastation;

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


}
