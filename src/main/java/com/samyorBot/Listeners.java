package com.samyorBot;

import com.samyorBot.commands.*;
import com.samyorBot.commands.embeds.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;
import com.samyorBot.commands.embeds.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Listeners extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        // Button click logic goes here
        String buttonId = event.getComponentId();

        TaxationInterface personalTax = new TaxationInterface();
        switch (buttonId) {
            /// HELP BUTTONS
            case "help_prev", "help_next":
                Help.handleButtonInteraction(event);
            case "char_prev", "char_next", "char_confirm":
                CharacterCreation.handleButtonInteraction(event);
                ///  TRAVEL BUTTONS
            case "travel_back", "move_city", "search_itinerary", "travel_nearby", "add_mercenaries", "add_armies",
                 "add_caravans":
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
            /// CITY BUTTONS
            case "city_tutorial", "city_demographics", "city_status", "city_enter":
                CityInterface.buttonHandler(event);
            /// ...
            default:
                event.reply("Unknown button clicked!").setEphemeral(true).queue();
                break;
        }
    }

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
}