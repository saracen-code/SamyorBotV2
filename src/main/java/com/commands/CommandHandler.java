package com.commands;

import com.commands.interfaces.*;
import com.commands.setup.CommandBase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandHandler extends ListenerAdapter {

    private final Map<String, CommandBase> commands = new HashMap<>();
    private final Map<Long, String> messageCommandMap = new HashMap<>(); // Tracks which message belongs to which command



    public CommandHandler(JDA jda) {
        // Register your commands
        commands.put("!phomo", new PhoMo());
        commands.put("!help", new Help());
        commands.put("!ping", new Ping());
        commands.put("!character", new CharacterInterface());
        commands.put("!city", new CityInterface());
        commands.put("!country", new CountryInterface());
        commands.put("!diplomacy", new DiplomacyInterface());
        commands.put("!investment", new InvestmentInterface());
        commands.put("!market", new MarketInterface());
        commands.put("!science", new ScienceInterface());
        commands.put("!taxation", new TaxationInterface());
        commands.put("!travel", new TravelInterface());


        // Register slash commands
        jda.updateCommands().addCommands(
                Commands.slash("phomo", "Handles phonotactic morphology")
                        .addOptions(new OptionData(OptionType.STRING, "input", "The text to analyze", true))
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        CommandBase cmd = commands.get(event.getName());
        if (cmd != null) {
            cmd.execute(event);
        } else {
            event.reply("Unknown command: " + event.getName()).setEphemeral(true).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().trim();
        if (message.startsWith("!")) {
            String command = message.substring(1).split(" ", 2)[0].toLowerCase();
            CommandBase cmd = commands.get(command);
            if (cmd != null) {
                cmd.execute(event);
            }
        }
    }

    // Handle button interactions
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        // Button click logic goes here
        String buttonId = event.getComponentId();

        TaxationInterface personalTax = new TaxationInterface();
        switch (buttonId) {
            ///  TRAVEL BUTTONS
            case "travel_back", "move_city", "search_itinerary", "travel_nearby", "add_mercenaries", "add_armies", "add_caravans":
                TravelInterface.buttonHandler(event);
                break;
            /// TAXATION BUTTONS
            case "tax_land", "tax_livestock", "tax_rent", "tax_main":
                personalTax.handleButtonClick(event);
                break;
            case "tax_increase1", "tax_increase5", "tax_decrease1", "tax_decrease5":
                personalTax.handleIncrementButtons(event);
                break;
            /// MARKET BUTTONS
            case "market_deeper", "market_crowd", "market_deals", "market_exit":
                MarketInterface.buttonHandler(event);
                break;
            /// INVESTMENT BUTTONS
            case "investment_tutorial", "auto_invest", "manual_invest":
                InvestmentInterface.buttonHandler(event);
                break;
            /// DIPLOMACY BUTTONS
            case "diplo_select_country", "diplo_view_actions":
                DiplomacyInterface.buttonHandler(event);
                break;
            /// COUNTRY BUTTONS
            case "request_census", "confirm_census_payment", "call_diplomacy_interface", "public_decree",
                 "manage_bureaucracy", "change_province_player":
                CountryInterface.buttonHandler(event);
                break;
            /// CHARACTER BUTTONS
            case "character_intro", "character_profiles", "character_create", "character_family", "character_actions",
                 "character_settings", "character_back", "select_culture":
                CharacterInterface.buttonHandler(event);
                break;
            /// ...
            default:
                event.reply("Unknown button clicked!").setEphemeral(true).queue();
                break;
        }
    }

    // Handle modal responses
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String modalId = event.getModalId();

        switch (modalId) {
            case "phomo_button":
                event.reply("You clicked the Phomo modal!").setEphemeral(true).queue();
                break;
            // Add cases for other modals as needed
            default:
                event.reply("Unknown button clicked!").setEphemeral(true).queue();
                break;
        }
    }

    public void trackCommandMessage(long messageId, String command) {
        messageCommandMap.put(messageId, command);
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        long messageId = event.getMessageIdLong();
        String relatedCommand = messageCommandMap.getOrDefault(messageId, "unknown");

        switch (emoji) {
            case "◀️": // Previous page in Help
            case "▶️": // Next page in Help
                if ("help".equals(relatedCommand)) {
                    Help.handleReaction(event);
                }
                break;
            case "✅": // Confirm action
                event.getChannel().sendMessage("Reaction confirmed! ✅ (Command: " + relatedCommand + ")").queue();
                break;
            case "❌": // Cancel action
                event.getChannel().sendMessage("Action canceled! ❌ (Command: " + relatedCommand + ")").queue();
                break;
            default:
                event.getChannel().sendMessage("Unknown reaction: " + emoji).queue();
                break;
        }
    }
}
