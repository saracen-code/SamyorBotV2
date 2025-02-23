package samyorbot.cogs.SamyorClasses;

import java.util.Random;

public class King extends Character {
    private DLNode<Character> family = new DLNode<>();
    private int fertility;
    private Character successor;
    public King(int discordID, int birthDate, int deathDate, Country citizenship,
                String name, int[] stats, double health) {
        super(discordID, birthDate, deathDate, citizenship, name, stats, health);
        Random random = new Random();
        this.fertility = random.nextInt(8);
    }
    // for testing
    public King() {
        super();
    }

    // actions
    public void getKid() {
    }
}
