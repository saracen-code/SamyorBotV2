package com.classes;

public class Military {
    private int lastCommanderRecruit;
    private Country country;
    private int[] army = new int[12];
    /* 0: Light Levies
     * 1: Cavalry
     * 2: Archers
     * 3: Knights
     * 4: Cultural Unit
     * 5: War Elephants or War Mammoths
     * 6: Azhi Cannon
     * 7: Automaton
     */
    private int[] navy = new int[4];
    /*
     * 0: Cogs
     * 1: Galleys
     * 2: Junks
     * 3: Cultural Unit
     */
    private int discipline;
    private int veteranship;
    private int contentment;
    private Player commander;
    private Player admiral;

    public Country getCountry() {
        return country;
    }

    public int[] getArmy() {
        return army;
    }
    public int[] getNavy() {
        return navy;
    }
    public int getDiscipline() {
        return discipline;
    }
    public int getVeteranship() {
        return veteranship;
    }
    public int getContentment() {
        return contentment;
    }
    public Player getCommander() {
        return commander;
    }
    public Player getAdmiral() {
        return admiral;
    }

    // edit
    public void addArmy(int index, int size) {
        army[index] = size;
    }
    public void addNavy(int index, int size) {
        navy[index] = size;
    }
    public void newCommander() {
        if (lastCommanderRecruit - Country.GLOBAL_YEAR > 6) {
            Player character = new Player(); // add culture getting feature
            this.commander = character;
            this.lastCommanderRecruit = Country.GLOBAL_YEAR;
        } else {
            throw new RuntimeException("A commander was made too recently.");
        }
    }
}
