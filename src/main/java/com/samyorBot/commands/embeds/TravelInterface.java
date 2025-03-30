package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import com.samyorBot.data.FunFacts;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class TravelInterface implements ICommand {
    public static Color interfaceColor = Color.decode("#777311");

    @Override
    public String getName() {
        return "travel";
    }

    @Override
    public String getDescription() {
        return "Provides options for managing travel, including routes, logistics and travel entourages.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        sendTravelMenu(event);
    }

    public static void buttonHandler(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        switch (buttonId) {
            case "travel_tutorial":
                sendTutorial(event);
                break;
            case "travel_actions":
                sendTravelActions(event);
                break;
            case "travel_logbook":
                sendLogbook(event);
                break;
            case "travel_entourage":
                sendEntourage(event);
                break;
            case "travel_stories":
                displayTravelStories(event);
                break;
            case "travel_back":
                break;
            case "back_welcome":
                break;
            default:
                event.reply("Unknown action").setEphemeral(true).queue();
                break;
        }
    }

    public static void sendTravelMenu(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("**❮ Travel Menu ❯**")
                .setDescription("Be warned, travels can be dangerous! Make sure you're prepared with supplies.\n\n" +
                        "Use the buttons below to navigate through the travel options.")
                .setFooter("May your travels be swift and safe.");
        FunFacts.addFunFacts(embed);

        // Buttons for navigation
        Button tutorialButton = Button.secondary("travel_tutorial", "📚 Go to Tutorial Menu");
        Button travelActionsButton = Button.success("travel_actions", "🚶‍♂️ Start Travel Actions");
        Button travelLogbookButton = Button.danger("travel_logbook", "📜 View Travel Logbook");
        Button travelEntourageButton = Button.secondary("travel_entourage", "🛡️ Manage Travel Entourage");
        Button travelStoriesButton = Button.primary("travel_stories", "📜 Travel Stories & Rumors");

        // Send the embed and make it ephemeral (only visible to the user who invoked the command)
        event.replyEmbeds(embed.build())
                .setActionRow(tutorialButton, travelActionsButton, travelLogbookButton, travelEntourageButton)
                .setEphemeral(true) // Make the message visible only to the user
                .queue();
    }



    public static void sendTutorial(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Travel Tutorial")
                .setDescription("Welcome to the Travel Tutorial!\n\n" +
                        "1. **Coins**: You need coins to travel. You'll be given an estimate for each trip, but unexpected expenses can occur.\n" +
                        "2. **Finding an Itinerary**: You can search for the shortest path from city A to city B.\n" +
                        "3. **Manual Movement**: You may also choose to move between cities manually.")
                .setFooter("Good luck on your travels!");

        Button backButton = Button.primary("travel_back", "Back to Welcome Page");

        event.editMessageEmbeds(embed.build())
                .setActionRow(backButton)
                .queue();
    }

    public static void sendTravelActions(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Travel Actions")
                .setDescription("Choose an action below to begin your journey!")
                .addField("Move to another city", "Select a city and travel there.", false)
                .addField("Search an Itinerary", "Find the best travel routes and their costs.", false)
                .addField("Travel Nearby", "Visit nearby cities listed.", false);
        FunFacts.addFunFacts(embed);

        // Buttons for actions
        Button moveButton = Button.primary("move_city", "Move to another city");
        Button itineraryButton = Button.primary("search_itinerary", "Search an itinerary");
        Button nearbyButton = Button.primary("travel_nearby", "Travel nearby cities");
        Button backButton = Button.primary("travel_back", "Back to Welcome Page");

        event.editMessageEmbeds(embed.build())
                .setActionRow(moveButton, itineraryButton, nearbyButton, backButton)
                .queue();
    }

    public static void sendLogbook(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Travel Logbook")
                .setDescription("Your last 20 travels:\n\n" +
                        "1. Visited **Kingston** and met with a scholar.\n" +
                        "2. Traveled to **Mornhill** and helped defend against bandits.\n" +
                        "3. Explored **Falbrook** and discovered a hidden cave.");
        FunFacts.addFunFacts(embed);

        Button backButton = Button.primary("back_welcome", "Back to Welcome Page");

        event.editMessageEmbeds(embed.build())
                .setActionRow(backButton)
                .queue();
    }

    public static void sendEntourage(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Manage Travel Entourage")
                .setDescription("Add mercenaries, armies, or trade caravans to your entourage for protection and supplies.")
                .addField("Mercenaries", "Hire mercenaries to protect you during travels.", false)
                .addField("Armies", "Recruit armies to support your journey.", false)
                .addField("Caravans", "Set up trade caravans for profitable travels.", false);
        FunFacts.addFunFacts(embed);

        Button mercenariesButton = Button.primary("add_mercenaries", "Add Mercenaries");
        Button armiesButton = Button.primary("add_armies", "Add Armies");
        Button caravansButton = Button.primary("add_caravans", "Add Caravans");
        Button backButton = Button.primary("travel_back", "Back to Welcome Page");

        event.editMessageEmbeds(embed.build())
                .setActionRow(mercenariesButton, armiesButton, caravansButton, backButton)
                .queue();
    }

    public static void displayTravelStories(ButtonInteractionEvent event) {
        // Array of travel stories and rumors
        String[] rumors = {
                "A group of adventurers in the northern city of Eldoria have discovered a hidden cave, filled with ancient artifacts. Some say it's cursed, while others swear by the treasure within.",
                "Rumor has it that a mysterious traveler has been spotted near the gates of the great city of Pylon. Some whisper that he carries news of a lost kingdom.",
                "Traders in the city of Turen claim that a great storm is brewing to the west. Sailors are being warned to stay at port, but no one knows exactly what is causing it.",
                "A wandering scholar recently visited the city of Alvoria and spoke of an ancient prophecy. Few believe it, but some say it's the key to untold power.",
                "The markets of Vensha are bustling, but some say the merchants are hiding something. A few rogue adventurers have gone missing near the city's outskirts, leaving rumors of dark dealings behind."
        };

        // Pick a random rumor
        Random random = new Random();
        String story = rumors[random.nextInt(rumors.length)];

        // Send the story to the user
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("🌍 Travel Stories & Rumors")
                .setDescription("Here’s a rumor from the roads and cities of the world:\n\n" + story)
                .setFooter("Remember, not all stories are true... or are they?");

        event.editMessageEmbeds(embed.build())
                .queue();
    }


}
