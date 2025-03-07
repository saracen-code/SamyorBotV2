package com.java.commands.taxation;

import com.java.commands.WorkingCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;

public class Taxation extends ListenerAdapter implements WorkingCommand {
    public String landTaxRate = "10";
    public String livestockTaxRate = "10";
    public String rentTaxRate = "10";

    @Override
    public void execute(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        System.out.println("[DEBUG] Received message: " + message);

        if (!message.equalsIgnoreCase("$taxation")) return;

        System.out.println("[DEBUG] Executing Taxation command...");

        /// make embed
        EmbedBuilder embed = new EmbedBuilder();
        // Send the home page with buttons (MessageReceivedEvent)
        createTaxation(embed, event);
    }

    public static void createTaxation(EmbedBuilder embed, MessageReceivedEvent event) {
        embed.setTitle("❮ **Taxation Management System** ❯")
                .setDescription("Welcome to the **Taxation Management System**. Here you can manage different taxes that impact the realm's economy.")
                .setColor(new Color(0xCDAA7D))
                .addField("📚 **Taxation System Pages**",
                        "Use the buttons below to navigate:\n" +
                                "🏞 **Land Tax** - Manage land tax.\n" +
                                "🐄 **Livestock Tax** - Manage livestock tax.\n" +
                                "🏠 **Rent Tax** - Manage rent tax.", false)
                .setFooter("Be wise, Your Majesty. Every tax has consequences.");

        System.out.println("[DEBUG] Embed built successfully.");

        // Create buttons
        Button landTaxButton = Button.primary("t.tax_land", "🏞 Go to Land Tax");
        Button livestockTaxButton = Button.primary("t.tax_livestock", "🐄 Go to Livestock Tax");
        Button rentTaxButton = Button.primary("t.tax_rent", "🏠 Go to Rent Tax");

        // Send the embed with buttons
        event.getChannel().sendMessageEmbeds(embed.build())
                .setActionRow(landTaxButton, livestockTaxButton, rentTaxButton)
                .queue(
                        success -> System.out.println("[DEBUG] Taxation menu sent successfully."),
                        failure -> System.err.println("[ERROR] Failed to send taxation menu: " + failure.getMessage())
                );
    }

    public static void createTaxation(EmbedBuilder embed, ButtonInteractionEvent event) {
        embed.setTitle("💰 **Taxation Management System** 💰")
                .setDescription("Welcome to the **Taxation Management System**. Here you can manage different taxes that impact the realm's economy.")
                .setColor(new Color(0xCDAA7D))
                .addField("📚 **Taxation System Pages**",
                        "Use the buttons below to navigate:\n" +
                                "🏞 **Land Tax** - Manage land tax.\n" +
                                "🐄 **Livestock Tax** - Manage livestock tax.\n" +
                                "🏠 **Rent Tax** - Manage rent tax.", false)
                .setFooter("Be wise, Your Majesty. Every tax has consequences.");

        System.out.println("[DEBUG] Embed built successfully.");

        // Create buttons
        Button landTaxButton = Button.primary("t.tax_land", "🏞 Go to Land Tax");
        Button livestockTaxButton = Button.primary("t.tax_livestock", "🐄 Go to Livestock Tax");
        Button rentTaxButton = Button.primary("t.tax_rent", "🏠 Go to Rent Tax");

        event.replyEmbeds(embed.build())
                .addActionRow(
                        Button.primary("t.tax_land", "🏞 Go to Land Tax"),
                        Button.primary("t.tax_livestock", "🐄 Go to Livestock Tax"),
                        Button.primary("t.tax_rent", "🏠 Go to Rent Tax")
                )
                .queue();

    }

    public void handleButtonClick(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        System.out.println("[DEBUG] Button clicked: " + buttonId);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCDAA7D));

        switch (buttonId) {
            case "t.tax_land":
                embed.setTitle("🏞️ **Land Tax Management** 🏞️")
                        .setDescription("Manage the land tax for the kingdom.\n\n**Current Rate:** " + landTaxRate + "%")
                        .addField("📊 **Adjust Land Tax**",
                                "Use the buttons below to adjust the tax rate.\n" +
                                        "*Example*: Use the increment buttons to adjust the rate by 1 or 5%.", false)
                        .setFooter("A fair land tax keeps your subjects happy.");
                break;

            case "t.tax_livestock":
                embed.setTitle("🐄 **Livestock Tax Management** 🐄")
                        .setDescription("Manage livestock taxation in the realm.\n\n**Current Rate:** " + livestockTaxRate + "%")
                        .addField("📊 **Adjust Livestock Tax**",
                                "Use the buttons below to adjust the tax rate.\n" +
                                        "*Example*: Use the increment buttons to adjust the rate by 1 or 5%.", false)
                        .setFooter("Proper taxation ensures a steady economy.");
                break;

            case "t.tax_rent":
                embed.setTitle("🏠 **Rent Tax Management** 🏠")
                        .setDescription("Manage taxation on housing and rent.\n\n**Current Rate:** " + rentTaxRate + "%")
                        .addField("📊 **Adjust Rent Tax**",
                                "Use the buttons below to adjust the tax rate.\n" +
                                        "*Example*: Use the increment buttons to adjust the rate by 1 or 5%.", false)
                        .setFooter("A stable housing market is vital for prosperity.");
                break;
            case "t.tax_main":
                new Taxation();
                break;
            default:
                event.reply("Unknown button clicked!").setEphemeral(true).queue();
                return;
        }

        // Add increment buttons (1 and 5 for each tax type)
        event.replyEmbeds(embed.build())
                .addActionRow(
                        Button.primary("t.increase_1", "🔼 Increase by 1%"),
                        Button.primary("t.decrease_1", "🔽 Decrease by 1%"),
                        Button.primary("t.increase_5", "🔼 Increase by 5%"),
                        Button.primary("t.decrease_5", "🔽 Decrease by 5%"),
                        Button.primary("t.tax_main", "🏰 Back to Main Menu")
                )
                .queue();
    }

    public void handleIncrementButtons(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        switch (buttonId) {
            case "t.increase_1":
                adjustTaxRate(event, 1);
                break;
            case "t.decrease_1":
                adjustTaxRate(event, -1);
                break;
            case "t.increase_5":
                adjustTaxRate(event, 5);
                break;
            case "t.decrease_5":
                adjustTaxRate(event, -5);
                break;
            default:
                event.reply("Unknown button action!").setEphemeral(true).queue();
                return;
        }

        // Send updated embed after adjustment
        handleButtonClick(event);
    }

    private void adjustTaxRate(ButtonInteractionEvent event, int amount) {
        // Determine which tax rate to adjust
        String buttonId = event.getButton().getId();

        if (buttonId.startsWith("t.tax_land")) {
            landTaxRate = String.valueOf(Math.max(0, Integer.parseInt(landTaxRate + amount))); // Ensure the rate doesn't go below 0%
        } else if (buttonId.startsWith("t.tax_livestock")) {
            livestockTaxRate = String.valueOf(Math.max(0, Integer.parseInt(livestockTaxRate + amount)));
        } else if (buttonId.startsWith("t.tax_rent")) {
            rentTaxRate = String.valueOf(Math.max(0, Integer.parseInt(rentTaxRate + amount)));
        }

        // Respond with a success message
        event.reply("Tax rate adjusted by " + amount + "%!").setEphemeral(true).queue();
    }
}
