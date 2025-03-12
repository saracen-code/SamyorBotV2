package com.java.commands.Interfaces;

import com.java.commands.WorkingCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;

public class CityInterface extends ListenerAdapter implements WorkingCommand {
    public static Color interfaceColor = Color.decode("#5b2d1a");

    @Override
    public void execute(MessageReceivedEvent event) {
        sendCityIntroPage(event.getChannel());
    }

    public void buttonHandler(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        switch (buttonId) {
            case "city_tutorial":
                sendCityTutorial(event);
                break;
            case "city_demographics":
                sendCityDemographics(event);
                break;
            case "city_status":
                sendCityStatus(event);
                break;
            case "enter_city":
                sendEnterCityPage(event);
                break;
            case "back_to_intro":
                sendCityIntroPage(event.getChannel());
                break;
            case "buy_specialty":
                // Handle buying city specialty
                break;
            case "setup_guild":
                // Handle setting up a guild
                break;
            case "setup_workshop":
                // Handle setting up a workshop
                break;
            case "open_trade_booth":
                // Handle opening a trade booth
                break;
            default:
                event.reply("Unknown action").setEphemeral(true).queue();
                break;
        }
    }


    public static void sendCityIntroPage(MessageChannel channel) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Welcome to the City!")
                .setDescription("Welcome to the heart of the medieval world. Here, you will build, manage, and grow a thriving city. " +
                        "Navigate through the options below to learn more and take action.")
                .setFooter("May your city flourish!");

        Button tutorialButton = Button.primary("city_tutorial", "📚 Learn How It Works");
        Button demographicsButton = Button.primary("city_demographics", "📊 View City Demographics");
        Button cityStatusButton = Button.primary("city_status", "🛡️ View City Status");
        Button enterCityButton = Button.primary("enter_city", "🏙️ Enter the City");

        // Send the welcome message with buttons
        channel.sendMessageEmbeds(embed.build())
                .setActionRow(tutorialButton, demographicsButton, cityStatusButton,enterCityButton)
                .queue();
    }

    public static void sendCityTutorial(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("City Tutorial")
                .setDescription("Here’s how the city system works:\n\n" +
                        "1. **City Demographics**: Learn about the population and development.\n" +
                        "2. **City Status**: Check the current condition and resources of the city.\n" +
                        "3. **Entering the City**: Buy city specialties, set up workshops, and more!")
                .setFooter("The city needs a strong leader like you!");

        Button backButton = Button.primary("back_to_intro", "Back to Intro");

        event.replyEmbeds(embed.build())
                .addActionRow(backButton)
                .queue();
    }


    public static void sendCityDemographics(ButtonInteractionEvent event) {
        // Simulated data
        int population = 5000;
        int taxRate = 10; // Percent
        String economicStatus = "Stable";
        String developmentLevel = "Growing Town (Tier 2)";

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("City Demographics")
                .setDescription("Here are the key demographic details of your city:")
                .addField("Population", String.valueOf(population), true)
                .addField("Tax Rate", taxRate + "%", true)
                .addField("Economic Status", economicStatus, true)
                .addField("Development Level", developmentLevel, true)
                .setFooter("The city is growing, but there are challenges ahead!");

        Button backButton = Button.primary("back_to_intro", "Back to Intro");

        event.replyEmbeds(embed.build())
                .addActionRow(backButton)
                .queue();
    }

    public static void sendCityStatus(ButtonInteractionEvent event) {
        // Simulated data
        String resourcesStatus = "Food: Adequate, Gold: Low, Wood: High";
        String infrastructureStatus = "Roads: Poor, Defense: Average, Health: Good";
        String overallStatus = "Stable, but needs attention to defense and infrastructure.";

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("City Status")
                .setDescription("Here's the current status of your city:")
                .addField("Resources", resourcesStatus, false)
                .addField("Infrastructure", infrastructureStatus, false)
                .addField("Overall Status", overallStatus, false)
                .setFooter("Manage wisely to keep the city prosperous!");

        Button backButton = Button.primary("back_to_intro", "Back to Intro");

        event.replyEmbeds(embed.build())
                .addActionRow(backButton)
                .queue();
    }

    public static void sendEnterCityPage(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Enter the City")
                .setDescription("You’ve entered the city gates. Here are your options:\n\n" +
                        "1. **Buy City Specialty**: Purchase unique items and resources.\n" +
                        "2. **Set Up a Guild**: Establish a guild to recruit followers and influence the city.\n" +
                        "3. **Set Up a Workshop**: Start crafting and producing goods.\n" +
                        "4. **Open a Trade Booth**: Speak with the mayor to set up a trade booth and engage in commerce.")
                .setFooter("The city is at your fingertips. What will you do?");

        Button buySpecialtyButton = Button.primary("buy_specialty", "Buy City Specialty");
        Button setupGuildButton = Button.primary("setup_guild", "Set Up Guild");
        Button setupWorkshopButton = Button.primary("setup_workshop", "Set Up Workshop");
        Button openTradeBoothButton = Button.primary("open_trade_booth", "Open Trade Booth");

        event.replyEmbeds(embed.build())
                .addActionRow(buySpecialtyButton, setupGuildButton, setupWorkshopButton, openTradeBoothButton)
                .queue();
    }


}
