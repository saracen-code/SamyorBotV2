package samyorbot.cogs.SamyorClasses;

public class Diplomacy {
    private Country country;
    private DLNode<Country> allies;
    private DLNode<Country> enemies;
    private DLNode<Country> warList;
    private DLNode<Country> treaties;

    // ----------------------------------GET

    public String getCountry() {
        return country.getName();
    }
    private DLNode<Country> getAllies() {
        return allies;
    }
    private DLNode<Country> getEnemies() {
        return enemies;
    }
    private DLNode<Country> getWarList() {
        return warList;
    }
    private DLNode<Country> getTreaties() {
        return treaties;
    }

    // ----------------------------------ADD/REMOVE
    public void addAlly(Country country) {
        this.getAllies().addLast(country);
    }
    public void removeAlly(Country country) {
        DLNode<Country> allies = this.getAllies();
    }
    public void addTaxTreaty(Country country, int expiration) {

    }
    public void addTradeTreaty(Country country, int expiration) {

    }
    public void addWrittenTreaty(Country country, int expiration, String content) {

    }
    public void removeWrittenTreaty(Country country, int expiration, String content) {
        // to implement
    }

}