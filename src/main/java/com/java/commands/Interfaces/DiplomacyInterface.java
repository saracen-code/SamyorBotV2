package com.java.commands.Interfaces;

import com.java.commands.WorkingCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;
import java.io.FileNotFoundException;

public class DiplomacyInterface implements WorkingCommand {

    private static final Color interfaceColor = new Color(0xAA8085); // Matching your project color scheme

    @Override
    public void execute(MessageReceivedEvent event) throws FileNotFoundException {
        sendDiplomacyIntro(event.getChannel());
    }

    /**
     * Sends the Diplomacy Intro Page with available actions.
     */
    public static void sendDiplomacyIntro(MessageChannel channel) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("❮ The Diplomatic Council ❯")
                .setDescription("The grand chamber is alight with murmurs, the scent of parchment and candlewax heavy in the air. Advisors lean in, foreign envoys watch keenly, and scribes stand ready to record your word as law.\n\n" +
                        "What path shall your realm take? **Will you weave alliances of steel and ink, or let the drums of war sound once more?**")
                .addField("🛡️ **Allies & Friendships**",
                        "Your throne stands unguarded by sworn brothers. No oaths have been exchanged, no hands clasped in brotherhood. **Will you extend the olive branch before another claims it, or steel yourself for the lonely road ahead?**",
                        false)
                .addField("⚔️ **Rivals & Hostilities**",
                        "For now, no banners are raised against you, no blades yet drawn. But beware—**whispers in the dark may stir armies, and a king without enemies is merely a king who has yet to be betrayed.**",
                        false)
                .setFooter("📜 \"History remembers not the cautious, but the bold. Choose wisely, Your Majesty.\"");

        // Buttons for interaction
        Button selectCountryButton = Button.primary("diplo_select_country", "Select Country");
        Button viewActionsButton = Button.secondary("diplo_view_actions", "View Diplomatic Actions");

        channel.sendMessageEmbeds(embed.build())
                .setActionRow(selectCountryButton, viewActionsButton)
                .queue();
    }

    /**
     * Handles button interactions for diplomacy.
     */
    public void buttonHandler(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        switch (buttonId) {
            case "diplo_select_country":
                sendCountrySelectionModal(event);
                break;
            case "diplo_view_actions":
                sendDiplomaticActions(event);
                break;
            default:
                event.reply("Unknown action.").setEphemeral(true).queue();
                break;
        }
    }

    /**
     * Sends a modal for country selection.
     */
    private void sendCountrySelectionModal(ButtonInteractionEvent event) {
        TextInput countryInput = TextInput.create("country_name", "Enter Country Name", TextInputStyle.SHORT)
                .setMinLength(1)
                .setMaxLength(50)
                .build();

        Modal countryModal = Modal.create("diplo_country_select", "Enter Country Name")
                .addActionRow(countryInput)
                .build();

        event.replyModal(countryModal).queue();
    }

    /**
     * Displays available diplomatic actions.
     */
    private void sendDiplomaticActions(ButtonInteractionEvent event) {
        EmbedBuilder actionsEmbed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("❮ Available Diplomatic Actions ❯")
                .setDescription("Your advisors await your command. Choose wisely, for every action will echo through the ages.")
                .addField("⚔ **War & Conflict**",
                        "Declare war, press claims, or demand tribute. A sword once drawn can seldom be sheathed without blood.",
                        false)
                .addField("🤝 **Relations & Alliances**",
                        "Secure peace, arrange marriages, or pledge an oath of brotherhood. Trust, once earned, is a powerful shield.",
                        false)
                .addField("📜 **Royal Decrees & Events**",
                        "Issue proclamations, call for festivities, or handle matters of courtly intrigue.",
                        false)
                .addField("🏰 **Military Access & Power**",
                        "Grant passage to armies, negotiate non-aggression pacts, or request reinforcements from loyal subjects.",
                        false)
                .addField("👑 **Vassalage & Dominion**",
                        "Swear fealty to a greater lord, demand tribute from weaker realms, or enforce suzerainty upon a lesser house.",
                        false)
                .addField("✍ **Treaties & Agreements**",
                        "Draft written treaties to cement alliances, trade agreements, or promises of non-aggression.",
                        false)
                .addField("🔮 **Intrigue & Shadowed Dealings**",
                        "Arrange assassinations, spread false rumors, or court the favor of spies and informants.",
                        false)
                .setFooter("Diplomacy is a game of patience and power.");

        event.replyEmbeds(actionsEmbed.build()).queue();
    }

    /**
     * Handles modal submissions for country selection.
     */
    public void modalHandler(ModalInteractionEvent event) {
        if (event.getModalId().equals("diplo_country_select")) {
            String countryName = event.getValue("country_name").getAsString();
            event.reply("You have selected: **" + countryName + "**").queue();
        }
    }
}
