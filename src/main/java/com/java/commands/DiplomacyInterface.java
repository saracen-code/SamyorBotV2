package com.java.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.modals.*;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

import java.awt.Color;
import java.io.FileNotFoundException;

public class DiplomacyInterface implements WorkingCommand {
    @Override
    public void execute(MessageReceivedEvent event) throws FileNotFoundException {
        // Create the Embed for Diplomacy Interface
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("**❮ The Diplomatic Council ❯**")
                .setAuthor(event.getAuthor().getName())
                .setDescription("The grand chamber is alight with murmurs, the scent of parchment and candlewax heavy in the air. Advisors lean in, foreign envoys watch keenly, and scribes stand ready to record your word as law. **What path shall your realm take?** Will you weave alliances of steel and ink, or let the drums of war sound once more?")
                .addField("🛡️ __**Allies & Friendships**__",
                        "Your throne stands unguarded by sworn brothers. No oaths have been exchanged, no hands clasped in brotherhood. **Will you extend the olive branch before another claims it, or steel yourself for the lonely road ahead?**",
                        true)
                .addField("⚔️ __**Rivals & Hostilities**__",
                        "For now, no banners are raised against you, no blades yet drawn. But beware—**whispers in the dark may stir armies, and a king without enemies is merely a king who has yet to be betrayed.**",
                        true)
                .addBlankField(true)
                .addField("\u200B", "══════════════════", false) // Ornate separator
                .setFooter("📜 \"History remembers not the cautious, but the bold. Choose wisely, Your Majesty.\"");


        FunFacts.addFunFacts(embed);

        // Create buttons
        Button inputCountryButton = Button.primary("d.select_country", "Enter Country Name");
        Button viewActionsButton = Button.secondary("d.view_actions", "View Diplomatic Actions");

        // Send the diplomacy menu with buttons
        event.getChannel().sendMessageEmbeds(embed.build())
                .setActionRow(inputCountryButton, viewActionsButton)
                .queue();
    }

    public void buttonHandler(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        if (buttonId.equals("d.select_country")) {
            // Country input modal
            TextInput countryInput = TextInput.create("country_name", "Enter the name of the country", TextInputStyle.SHORT)
                    .setMinLength(1)
                    .setMaxLength(50)
                    .build();

            Modal countryModal = Modal.create("s.country", "Enter Country Name")
                    .addActionRow(countryInput)
                    .build();

            event.replyModal(countryModal).queue();
        }
        else if (buttonId.equals("d.view_actions")) {
            // Create second embed for available diplomatic actions
            EmbedBuilder actionsEmbed = new EmbedBuilder();
            actionsEmbed.setTitle("**❮Available Diplomatic Actions❯**")
                    .setDescription("Your advisors await your command. Choose wisely, for every action will echo through the ages.")
                    .addField("⚔ **War & Conflict**",
                            "Declare war, press claims, or demand tribute. A sword once drawn can seldom be sheathed without blood.",
                            true)
                    .addField("🤝 **Relations & Alliances**",
                            "Secure peace, arrange marriages, or pledge an oath of brotherhood. Trust, once earned, is a powerful shield.",
                            true)
                    .addField("📜 **Royal Decrees & Events**",
                            "Issue proclamations, call for festivities, or handle matters of courtly intrigue.",
                            true)
                    .addField("🏰 **Military Access & Power**",
                            "Grant passage to armies, negotiate non-aggression pacts, or request reinforcements from loyal subjects.",
                            true)
                    .addField("👑 **Vassalage & Dominion**",
                            "Swear fealty to a greater lord, demand tribute from weaker realms, or enforce suzerainty upon a lesser house.",
                            true)
                    .addField("✍ **Treaties & Agreements**",
                            "Draft written treaties to cement alliances, trade agreements, or promises of non-aggression.",
                            true)
                    .addField("🔮 **Intrigue & Shadowed Dealings**",
                            "Arrange assassinations, spread false rumors, or court the favor of spies and informants.",
                            true)
                    .setFooter("Diplomacy is a game of patience and power.");

            event.replyEmbeds(actionsEmbed.build()).queue();
        }
    }

    public void modalHandler(ModalInteractionEvent event) {
        if (event.getModalId().equals("s.country")) {
            String countryName = event.getValue("country_name").getAsString();
            event.reply("You entered: " + countryName).queue();
        }
    }
}
