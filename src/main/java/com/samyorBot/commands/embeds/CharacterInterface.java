package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import com.samyorBot.classes.Player;
import com.samyorBot.data.FunFacts;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterInterface implements ICommand {
    public static Color interfaceColor = Color.decode("#777311");

    private String culture;
    private String discordID;
    private String charName;
    private String description;
    private String skillsDescription;
    private String imageURL;

    public CharacterInterface() {
        this.charName = "Samyor";
    }

    @Override
    public String getName() {
        return "character";
    }

    @Override
    public String getDescription() {
        return " Manages character selection, creation, customization and your character logbook.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("");
        sendCharacterMenu(event.getChannel());
    }


    public static void buttonHandler(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        switch (buttonId) {
            case "character_intro":
                sendCharacterIntro(event);
                break;
            case "character_profiles":
                sendCharacterProfiles(event);
                break;
            case "character_create":
                sendCharacterCreation(event);
                break;
            case "character_family":
                sendFamilyTree(event);
                break;
            case "character_actions":
                sendCharacterActions(event);
                break;
            case "character_settings":
                sendCharacterSettings(event);
                break;
            case "character_back":
                sendCharacterMenu(event.getChannel());
                break;
            default:
                event.reply("Unknown action").setEphemeral(true).queue();
                break;
        }
    }

    public static void sendCharacterMenu(MessageChannel channel) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("**❮ Character Interface ❯**")
                .setDescription("Manage your characters and their interactions in the world.\n\n" +
                        "Use the buttons below to navigate through the character options.")
                .setFooter("Your legacy begins here.");

        FunFacts.addFunFacts(embed);

        Button introButton = Button.secondary("character_intro", "📜 Character Introduction");
        Button profilesButton = Button.success("character_profiles", "📖 View Profiles");
        Button createButton = Button.primary("character_create", "📝 Create Character");
        Button familyButton = Button.secondary("character_family", "🌳 Family Tree");
        Button actionsButton = Button.primary("character_actions", "⚔ Character Actions");

        channel.sendMessageEmbeds(embed.build())
                .setActionRow(introButton, profilesButton, createButton, familyButton, actionsButton)
                .queue();
    }

    public static void sendCharacterIntro(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Character Types & Roles")
                .setDescription("""
                        Welcome to the **Character System**!
                        
                        **Types of Characters:**
                        - 👑 **Rulers**: Kings, queens, and lords who govern lands and make policies.
                        - ⚔ **Adventurers**: Explorers, mercenaries, and travelers seeking fortune.
                        - 🏛 **Scholars**: Knowledge seekers who research, write, and influence society.
                        - 🏗 **Merchants**: Traders who control the flow of resources and economy.
                        
                        Choose your path wisely!""")
                .setFooter("Every character has a story to tell.");

        Button backButton = Button.primary("character_back", "Back to Character Menu");

        event.editMessageEmbeds(embed.build()).setActionRow(backButton).queue();
    }

    public static void sendCharacterProfiles(ButtonInteractionEvent event) {
        List<Player> characters = Player.getCharacters();

        for (Player character : characters) {
            System.out.println(character);
        }

        if (characters.isEmpty()) {
            event.getChannel().sendMessage("You have no registered characters! Networking is indeed important...").queue();
            return;
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.decode("#777311")) // Using the same color scheme
                .setTitle("📜 Your Character Profiles")
                .setFooter("More profile features coming soon!");

        for (Player character : characters) {
            embed.addField(String.valueOf(character.getID()), character.toString(), false);
        }

        event.replyEmbeds(embed.build()).queue();
    }

    public static void sendCharacterCreation(ButtonInteractionEvent event) {
        new CharacterCreation().execute(event);
    }


    public static void sendFamilyTree(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("🌳 Family Tree")
                .setDescription("View your lineage and family connections. Track generations and uncover legacies.")
                .setFooter("Bloodlines tell stories of the past.");

        Button backButton = Button.primary("character_back", "Back to Character Menu");

        event.replyEmbeds(embed.build()).addActionRow(backButton).queue();
    }

    public static void sendCharacterActions(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("⚔ Character Actions")
                .setDescription("Choose an action below to shape your story!")
                .addField("📜 Make a Decision", "Take actions that influence your fate.", false)
                .addField("⚖ Negotiate", "Engage in trade or diplomacy.", false)
                .addField("🏹 Engage in Combat", "Battle enemies or defend your realm.", false);

        Button decisionButton = Button.primary("character_decision", "📜 Make a Decision");
        Button negotiateButton = Button.primary("character_negotiate", "⚖ Negotiate");
        Button combatButton = Button.primary("character_combat", "🏹 Engage in Combat");
        Button backButton = Button.primary("character_back", "Back to Character Menu");

        event.replyEmbeds(embed.build()).addActionRow(decisionButton, negotiateButton, combatButton, backButton).queue();
    }

    public static void sendCharacterSettings(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("⚙ Character Settings")
                .setDescription("Customize your character settings, including appearance, abilities, and personal preferences.")
                .setFooter("Your character, your rules.");

        Button backButton = Button.primary("character_back", "Back to Character Menu");

        event.replyEmbeds(embed.build()).addActionRow(backButton).queue();
    }
}