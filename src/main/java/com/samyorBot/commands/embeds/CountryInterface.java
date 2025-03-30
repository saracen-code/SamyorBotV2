package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import com.samyorBot.data.FunFacts;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class CountryInterface implements ICommand {

    public static Color interfaceColor = Color.decode("#5b2d1a");
    // Example country information (you can replace these with dynamic data)
    public static int population = 1000000;
    public static int militaryStrength = 5000;
    public static double economyStrength = 75.0; // Percentage of full capacity
    public static double corruptionLevel = 35.0; // Percentage of corruption
    public static int provinces = 5;
    private static final int censusCost = 500;

    @Override
    public String getName() {
        return "country";
    }

    @Override
    public String getDescription() {
        return "Comprehensive menu for the management of country affairs.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.replyEmbeds(sendCountryIntroPage())
                .setActionRow(
                    Button.primary("request_census", "Conduct Census"),
                    Button.primary("call_tax_interface", "Manage Taxes"),
                    Button.primary("call_diplomacy_interface", "Diplomacy"),
                    Button.primary("public_decree", "Public Decrees"))
                .setEphemeral(true)
                .queue();
    }


    public static void buttonHandler(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        switch (buttonId) {
            case "request_census":
                // Step 1: Ask the player to pay before revealing census
                CountryInterface.requestCensusPayment(event);
                break;

            case "confirm_census_payment":
                // Step 2: Deduct gold and show census if they can afford it
                CountryInterface.conductCensus(event);
                break;
                /*
            case "call_tax_interface":
                callTaxInterface(event);
                break;

                 */
            case "call_diplomacy_interface":
                callDiplomacyInterface(event);
                break;
            case "public_decree":
                publicDecree(event);
                break;
            case "manage_bureaucracy":
                manageBureaucracy(event);
                break;
            case "change_province_player":
                changeProvincePlayer(event);
                break;
            default:
                event.reply("Unknown action").setEphemeral(true).queue();
                break;
        }
    }


    public static MessageEmbed sendCountryIntroPage() {

        // EmbedBuilder to create the intro page
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Welcome to Your Country")
                .setDescription("Welcome to the control panel of your nation. Here, you can manage everything from taxes to diplomacy and beyond.\n\n" +
                        "Here’s a quick overview of your nation’s current state:\n\n" +
                        "**Population**: " + population + " people\n" +
                        "**Military Strength**: " + militaryStrength + " soldiers\n" +
                        "**Economic Strength**: " + economyStrength + "% of full capacity\n" +
                        "**Corruption Level**: " + corruptionLevel + "%\n" +
                        "**Provinces**: " + provinces + " provinces under your rule\n\n" +
                        "Use the options below to get started with managing your country.")
                .setFooter("Manage wisely, or face the consequences!");
        FunFacts.addFunFacts(embed);

        return embed.build();
    }

    /*

    public static void callTaxInterface(ButtonInteractionEvent event) {
        // Calling existing tax interface
        TaxationInterface.sendTaxationIntro(event.getChannel()); // Assuming sendTaxInterface is your existing method for taxes
    }
     */


    public static void callDiplomacyInterface(ButtonInteractionEvent event) {
        // Calling existing diplomacy interface
        DiplomacyInterface.sendDiplomacyIntro(event.getChannel(), event.getUser()); // Assuming sendDiplomacyInterface is your existing method for diplomacy
    }


    public static void publicDecree(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Public Decree")
                .setDescription("Proclaim a new public decree or announcement for the citizens.")
                .addField("Example Decree", "Example: All citizens are to pay taxes by the end of the month!", false)
                .setFooter("The will of the state.");

        Button confirmButton = Button.primary("confirm_decree", "Issue Decree");

        event.editMessageEmbeds(embed.build())
                .setActionRow(confirmButton)
                .queue();
    }

    public static void manageBureaucracy(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Manage Bureaucracy")
                .setDescription("Here, you can manage your bureaucracy and government officials.")
                .addField("Assign Officials", "Choose officials to oversee various aspects of the government.", false)
                .addField("Allocate Resources", "Decide how to allocate resources for better efficiency.", false)
                .addField("Reform Institutions", "Reform parts of the bureaucracy for greater effectiveness.", false)
                .setFooter("Bureaucratic efficiency is key to a strong nation.");

        Button assignButton = Button.primary("assign_officials", "Assign Government Officials");
        Button allocateButton = Button.primary("allocate_resources", "Allocate Resources");
        Button reformButton = Button.primary("reform_institutions", "Reform Bureaucracy");

        event.replyEmbeds(embed.build())
                .addActionRow(assignButton, allocateButton, reformButton)
                .queue();
    }

    public static void changeProvincePlayer(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Change Province Player")
                .setDescription("You can assign a new player to manage a specific province of your country. Choose carefully, as this could change the balance of power.");

        Button assignButton = Button.primary("assign_province", "Assign New Player to Province");

        event.editMessageEmbeds(embed.build())
                .setActionRow(assignButton)
                .queue();
    }

    /**
     * Step 1: Ask the player to pay for the census before revealing results.
     */
    public static void requestCensusPayment(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Conduct Census")
                .setDescription("A full demographic survey requires funding. \n\n**Cost:** " + censusCost + " Gold.\n\n" +
                        "Would you like to proceed with the census?")
                .setFooter("A well-informed ruler is a wise ruler.");

        Button payButton = Button.success("confirm_census_payment", "Pay " + censusCost + " Gold");

        event.editMessageEmbeds(embed.build())
                .setActionRow(payButton)
                .queue();
    }

    /**
     * Step 2: Deduct payment and reveal census data if the player has enough gold.
     */
    public static void conductCensus(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        if (buttonId.equals("confirm_census_payment")) {
            if (playerHasEnoughGold(event.getUser().getId(), censusCost)) {
                deductGold(event.getUser().getId(), censusCost);
                revealCensusData(event);
            } else {
                event.reply("❌ You do not have enough gold to conduct a census.").setEphemeral(true).queue();
            }
        }
    }

    /**
     * Step 3: Reveal census data after payment.
     */
    private static void revealCensusData(ButtonInteractionEvent event) {
        Random rand = new Random();
        double growthRate = rand.nextDouble() * 5 + 1; // 1-5%
        double corruptionLevel = rand.nextDouble() * 30; // Random corruption percentage
        int population = rand.nextInt(50000) + 10000; // 10k - 60k population

        double[] groupPercentages = {0.10, 0.20, 0.60, 0.10}; // Noble, Merchant, Peasant, Soldier

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("📜 Country Census Report")
                .setDescription("The latest census has been completed. Here are the findings:")
                .addField("👥 Total Population", String.format("%,d", population), true)
                .addField("📈 Growth Rate", String.format("%.2f%%", growthRate), true)
                .addField("⚖️ Corruption Level", String.format("%.2f%%", corruptionLevel), true)
                .addField("🏛️ Population Breakdown",
                        String.format("🔹 Noble: %.2f%%\n🔹 Merchant: %.2f%%\n🔹 Peasant: %.2f%%\n🔹 Soldier: %.2f%%",
                                groupPercentages[0] * 100, groupPercentages[1] * 100, groupPercentages[2] * 100, groupPercentages[3] * 100),
                        false)
                .setFooter("Use this data wisely to shape your nation's future.");

        event.editMessageEmbeds(embed.build())
                .setActionRow()
                .queue();
    }

    /**
     * Placeholder method: Check if a player has enough gold.
     * Replace this with your actual economy system.
     */
    private static boolean playerHasEnoughGold(String playerId, int cost) {
        // Implement actual gold-checking logic here
        return true; // Placeholder: Always returns true
    }

    /**
     * Placeholder method: Deduct gold from the player's balance.
     * Replace this with your actual economy system.
     */
    private static void deductGold(String playerId, int amount) {
        // Implement actual gold deduction logic here
    }
}

