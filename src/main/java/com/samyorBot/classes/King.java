package com.samyorBot.classes;

public class King extends Player {
    private DLNode<Player> family = new DLNode<>();
    private int fertility;
    private Player successor;
    int successorID;

    public King(int ID, String charName, String type, int fertility, int successorID, int personalFunds, int investmentReputation,
                              long discordID, int birthDate, int deathDate, int duelStrength, int azhiStrength, int militaryPoint,
                              int persuasion, int stewardshipPoint, int wisdomPoint, int vitality, boolean isAlive) {

        super(ID, charName, type, fertility, personalFunds, investmentReputation, discordID, birthDate, deathDate,
                duelStrength, azhiStrength, militaryPoint, persuasion, stewardshipPoint, wisdomPoint, vitality, isAlive);
        this.successorID = successorID;

    }

    // for testing
    public King() {
        super();
    }

    // actions
    public void getKid() {
    }
}
