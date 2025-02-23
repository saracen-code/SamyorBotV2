package samyorbot.cogs.SamyorClasses;

public class Province {
    private Country country;
    private String name;

    public Province(Country country, String name) {
        this.country = country;
        this.name = name;
    }

    // Getter and Setter for country
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
