package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;
import java.util.List;

public class TaxationInterface implements ICommand {
    public String landTaxRate = "10";
    public String livestockTaxRate = "10";
    public String rentTaxRate = "10";

    @Override
    public String getName() {
        return "taxation";
    }

    @Override
    public String getDescription() {
        return "Control your tax rates and change taxation policies";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        createTaxation(embed, event.getChannel());
    }

    public static void createTaxation(EmbedBuilder embed, MessageChannel channel) {
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
        Button landTaxButton = Button.primary("tax_land", "🏞 Go to Land Tax");
        Button livestockTaxButton = Button.primary("tax_livestock", "🐄 Go to Livestock Tax");
        Button rentTaxButton = Button.primary("tax_rent", "🏠 Go to Rent Tax");

        // Send the embed with buttons
        channel.sendMessageEmbeds(embed.build())
                .setActionRow(landTaxButton, livestockTaxButton, rentTaxButton)
                .queue(
                        success -> System.out.println("[DEBUG] Taxation menu sent successfully."),
                        failure -> System.err.println("[ERROR] Failed to send taxation menu: " + failure.getMessage())
                );
    }

    public void handleButtonClick(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        System.out.println("[DEBUG] Button clicked: " + buttonId);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCDAA7D));

        switch (buttonId) {
            case "tax_land":
                embed.setTitle("🏞️ **Land Tax Management** 🏞️")
                        .setDescription("Manage the land tax for the kingdom.\n\n**Current Rate:** " + landTaxRate + "%")
                        .addField("📊 **Adjust Land Tax**",
                                "Use the buttons below to adjust the tax rate.\n" +
                                        "*Example*: Use the increment buttons to adjust the rate by 1 or 5%.", false)
                        .setFooter("A fair land tax keeps your subjects happy.");
                break;

            case "tax_livestock":
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
            case "tax_main":
                new TaxationInterface();
                break;
            default:
                event.reply("Unknown button clicked!").setEphemeral(true).queue();
                return;
        }

        // Add increment buttons (1 and 5 for each tax type)
        event.replyEmbeds(embed.build())
                .addActionRow(
                        Button.primary("tax_increase1", "🔼 Increase by 1%"),
                        Button.primary("tax_decrease1", "🔽 Decrease by 1%"),
                        Button.primary("tax_increase5", "🔼 Increase by 5%"),
                        Button.primary("tax_decrease5", "🔽 Decrease by 5%"),
                        Button.primary("tax_main", "🏰 Back to com.samyorBot.Main Menu")
                )
                .queue();
    }

    public void handleIncrementButtons(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        switch (buttonId) {
            case "tax_increase1":
                adjustTaxRate(event, 1);
                break;
            case "tax_decrease1":
                adjustTaxRate(event, -1);
                break;
            case "tax_increase5":
                adjustTaxRate(event, 5);
                break;
            case "tax_decrease5":
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
