package samyorbot.cogs.SamyorClasses;

public class Taxes {
    private static int[] PROBLEMATIC_VALUES = {30, 30, 20, 12, 20, 4000};
    private static int[] DEFAULT_TAXES = {15, 15, 15, 15, 15, 15};
    private Country country;
    private int[] taxStats = new int[6];
    private int budget;
    private int annualIncome;
    private int annualExpenses;

    public Taxes(Country country, int budget, int annualIncome, int annualExpenses) {
        this.country = country;
        taxStats = Taxes.DEFAULT_TAXES;
        this.budget = budget;
        this.annualIncome = annualIncome;
        this.annualExpenses = annualExpenses;
    }
    public Taxes(Country country, int[] taxStats) {
        this.country = country;
        this.taxStats = taxStats;
        this.budget = budget;
        this.annualIncome = annualIncome;
        this.annualExpenses = annualExpenses;
    }

    // GET
    public int[] getTaxStats() {
        return taxStats;
    }
    public int getSpecificStat(int index) {
        if (index <= taxStats.length) {
            return taxStats[index];
        } else {
            throw new ArrayIndexOutOfBoundsException("Out of bounds: This Statistic does not exist.");
        }
    }

    //SET
    public void setCountry(Country country) {
        this.country = country;
    }
    public void setTax(int index, int value) {
        if (index <= taxStats.length) {
            taxStats[index] = value;
        } else {
            throw new ArrayIndexOutOfBoundsException("Out of bounds: This Statistic does not exist.");
        }
    }
    public void incrementTax(int index, int increment) {
        if (index <= taxStats.length) {
            taxStats[index] += increment;
        } else {
            throw new ArrayIndexOutOfBoundsException("Out of bounds: This Statistic does not exist.");
        }
    }
}
