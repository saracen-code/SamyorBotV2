package com.samyorBot;

import com.samyorBot.commands.Help;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CommandHandler extends ListenerAdapter {
    /*
    private final Map<Long, String> messageCommandMap = new HashMap<>(); // Tracks which message belongs to which command
    private static List<ICommand> commands = new ArrayList<>();

    public static void loadCommands(JDA jda) {
        for(Guild guild : jda.getGuilds()) {
            for(ICommand command : commands) {
                if(command.getOptions() == null) {
                    guild.upsertCommand(command.getName(), command.getDescription()).queue();
                } else {
                    guild.upsertCommand(command.getName(), command.getDescription()).addOptions(command.getOptions()).queue();
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for(ICommand command : commands) {
            if(command.getName().equals(event.getName())) {
                command.execute(event);
                return;
            }
            else{
                event.reply("Unknown command: " + event.getName()).setEphemeral(true).queue();
            }
        }
    }

    public void add(ICommand command) {
        commands.add(command);
    }

    // Constructor for CommandHandler
    public CommandHandler(JDA jda) {
        // Register your commands here
        commands.put("phomo", new PhoMo());
        commands.put("help", new Help());
        commands.put("ping", new Ping());
        commands.put("character", new CharacterInterface());
        commands.put("city", new CityInterface());
        commands.put("country", new CountryInterface());
        commands.put("diplomacy", new DiplomacyInterface());
        commands.put("investment", new InvestmentInterface());
        commands.put("market", new MarketInterface());
        commands.put("science", new ScienceInterface());
        commands.put("taxation", new TaxationInterface());
        commands.put("travel", new TravelInterface());
    }
    // Handle button interactions
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        // Button click logic goes here
        String buttonId = event.getComponentId();

        TaxationInterface personalTax = new TaxationInterface();
        switch (buttonId) {
            /// HELP BUTTONS
            case "help_prev", "help_next":
                Help.handleButtonInteraction(event);
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

 */
}
