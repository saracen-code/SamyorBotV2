package com.java.commands.Interfaces;

import com.java.classes.Culture;
import com.java.classes.Player;
import com.java.commands.FunFacts;
import com.java.commands.WorkingCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.Color;
import java.util.List;

public class CharacterInterface extends ListenerAdapter implements WorkingCommand {
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
    public void execute(MessageReceivedEvent event) {
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
            case "select_culture":
                sendCulturePage(event);
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

        event.replyEmbeds(embed.build()).addActionRow(backButton).queue();
    }

    public static void sendCharacterProfiles(ButtonInteractionEvent event) {
        List<Player> characters = Player.getCharacters();
        if (characters.isEmpty()) {
            event.getChannel().sendMessage("You have no characters!").queue();
            return;
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.decode("#777311")) // Using the same color scheme
                .setTitle("📜 Your Character Profiles")
                .setFooter("More profile features coming soon!");

        for (Player character : characters) {
            embed.addField(String.valueOf(character.getID()), character.toString(), false);
        }

        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }

    public static void sendCharacterCreation(ButtonInteractionEvent event) {
        String step = event.getComponentId();  // Use component IDs to track the current step

        switch (step) {
            case "intro":
                sendIntroPage(event);
                break;

            case "culture":
                sendCulturePage(event);
                break;

            case "description":
                sendDescriptionPage(event);
                break;

            case "characterType":
                sendCharacterTypePage(event);
                break;

            case "finalPage":
                sendFinalPage(event);
                break;

            default:
                sendIntroPage(event);
                break;
        }
    }

    private static void sendIntroPage(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Character Creation - Introduction");
        embed.setDescription("Welcome to the character creation process! Please note: you can only have 5 saved characters, and incomplete or low-quality submissions may be deleted by staff.");
        embed.addField("Time Limit", "You have 5 minutes to complete this form.", false);
        embed.setFooter("You may be asked to provide quality details about your character.");

        // Buttons to continue to the next step
        Button nextButton = Button.primary("select_culture", "Select Culture");
        event.getMessage().editMessageEmbeds(embed.build()).setActionRow(nextButton).queue();
    }

    private static void sendCulturePage(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Character Creation - Culture Selection");
        embed.setDescription("What culture does your character belong to? Use the reactions below to choose.");
        embed.addField(Emoji.fromUnicode("\uD83C\uDFDC") + "Dabanites", "The Dabanites are a proud and honorable people of the Sakh-har (desert), bound by strict commitments and tribal loyalty, who, after unification under the Receiver Mutalâqi, adhere to a doctrine-centered society with a focus on religious duty, historical legacy, and a strong, though distant, sense of community.", true);
        embed.addField(Emoji.fromUnicode("\uD83C\uDFDC") + "Dabanites", "The Dabanites are a proud and honorable people of the Sakh-har (desert), bound by strict commitments and tribal loyalty, who, after unification under the Receiver Mutalâqi, adhere to a doctrine-centered society with a focus on religious duty, historical legacy, and a strong, though distant, sense of community.", true);

        event.getMessage().editMessageEmbeds(embed.build()).queue(message -> {
            message.addReaction(Emoji.fromUnicode("\uD83C\uDFDC")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
            message.addReaction(Emoji.fromUnicode("👍")).queue();
        } );
    }

    public void cultureSelection(MessageReactionAddEvent event, CharacterInterface newCharacter) {
        String emoji = String.valueOf(event.getEmoji());
        newCharacter.discordID = event.getUserId();
        switch (emoji) {
            case "\uD83C\uDFDC":
                newCharacter.culture = "Dabanite";
                break;
        }
        sendNameSelectionPage(event);
    }

    public static void sendNameSelectionPage(MessageReactionAddEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Character Creation - Name Selection");
        embed.setDescription("Would you like a randomly generated name?");

        // Buttons for random name option
        Button yesButton = Button.primary("randomNameYes", "Yes");
        Button noButton = Button.primary("randomNameNo", "No");
        event.getChannel().sendMessageEmbeds(embed.build()).setActionRow(yesButton, noButton).queue();
    }

    private static void nameSelection(ButtonInteractionEvent event) {

    }

    private static void sendDescriptionPage(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Character Creation - Character Description");
        embed.setDescription("Please write a short description of your character. What are their skills and defects?");
        embed.addField("Note", "Be careful! Your description will directly impact your profile.", false);

        // Possibly add a button to proceed once the description is submitted
        Button nextButton = Button.primary("characterType", "Select Character Type");
        event.getMessage().editMessageEmbeds(embed.build()).setActionRow(nextButton).queue();
    }

    private static void sendCharacterTypePage(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Character Creation - Character Type");
        embed.setDescription("Select your character type (King, Statesman, Adventurer, Scholar, Commoner). Only approved roles will be available.");

        // Buttons for character type selection
        Button kingButton = Button.primary("king", "King");
        Button statesmanButton = Button.primary("statesman", "Statesman");
        Button adventurerButton = Button.primary("adventurer", "Adventurer");
        Button scholarButton = Button.primary("scholar", "Scholar");
        Button commonerButton = Button.primary("commoner", "Commoner");

        event.getMessage().editMessageEmbeds(embed.build()).setActionRow(kingButton, statesmanButton, adventurerButton, scholarButton, commonerButton).queue();
    }

    private static void sendFinalPage(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Character Creation - Completed");
        embed.setDescription("Congratulations! You have completed your character profile.");
        // Add the character's final details in the embed here

        event.getMessage().editMessageEmbeds(embed.build()).queue();
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
