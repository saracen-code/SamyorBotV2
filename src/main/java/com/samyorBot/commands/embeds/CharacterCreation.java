package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import com.samyorBot.data.FunFacts;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterCreation implements ICommand {
    public static final Color commandColor = Color.decode("#777311");
    private static final Map<Long, Integer> culturePageMap = new HashMap<>();
    private static final int TOTAL_PAGES = 10;

    @Override
    public String getName() {
        return "charcreate";
    }

    @Override
    public String getDescription() {
        return "Enables you to create a new character.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        sendCharCreation(event.getChannel());
    }

    public void execute(ButtonInteractionEvent event) {
        sendCharCreation(event.getChannel());
    }

    private void sendCharCreation(MessageChannel channel) {
        EmbedBuilder embed = getCharPage(1);
        channel.sendMessageEmbeds(embed.build())
                .setActionRow(
                        Button.primary("char_prev", "◀️ Previous").asDisabled(),
                        Button.primary("char_next", "Next ▶️")
                )
                .queue(message -> {
                    culturePageMap.put(message.getIdLong(), 1);
                });
    }

    private EmbedBuilder getCharPage(int page) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(commandColor);
        embed.setFooter("Use ◀️/▶️ buttons to navigate.");

        switch (page) {
            case 1:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 1: CULTURE__", "", false)
                        .addField("The Sebeh", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Ehkiyas", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Siynimekiy", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Nrayephet", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Knazit", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Egyi-an", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Nyiteriym", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Arunnaya", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Sakh'hari", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Efniredar", "Lorem Ipsum Dolor et Simet.", false)
                        .addField("The Iymkinethuy", "Lorem Ipsum Dolor et Simet.", false);
                FunFacts.addFunFacts(embed);
                break;
            case 2:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 2: NAME__", "", false)
                        .addField("Randomize Sask", "Obtain a random name by culture.", false)
                        .addField("Browse Names", "🚧 Browse a list of names by Saskartan culture.", false)
                        .addField("Manual Input", "🚧 Manually input your character's name.", false);
                FunFacts.addFunFacts(embed);
                break;
            case 3:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 3: START LOCATION__", "", false)
                        .addField("Write", "Write the name of a city to select it.", false);
                FunFacts.addFunFacts(embed);
                break;
            case 4:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 4: BIRTH DATE__", "", false)
                        .addField("Write", "Write your birth date.", false);
                FunFacts.addFunFacts(embed);
                break;
            case 5:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 5: DESCRIPTION (OPTIONAL)__", "", false)
                        .addField("Write", "Write a character description or backstory (optional).", false);
                FunFacts.addFunFacts(embed);
            case 6:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 5: SKILL DESCRIPTION__", "", false)
                        .addField("Write", "Write a character's skill backstory description.", false);
                FunFacts.addFunFacts(embed);
            case 7:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 5: DESCRIPTION (OPTIONAL)__", "", false)
                        .addField("Write", "Write a character's skill backstory description.", false);
                FunFacts.addFunFacts(embed);
            case 8:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 5: PORTRAIT (OPTIONAL)__", "", false)
                        .addField("Write", "Provide the URL for your character's portrait.", false);
                FunFacts.addFunFacts(embed);
                break;
            case 9:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 5: FAMILY__", "", false)
                        .addField("Setup", "Write down the IDs of already-created members in your character's family relations.", false);
                FunFacts.addFunFacts(embed);
            case 10:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__COMPLETED__", "", false)
                        .addField("Setup", "CHARACTER CREATION IS COMPLETE, YOUR CHARACTER HAS ID: ___", false);
                FunFacts.addFunFacts(embed);
        }
        return embed;
    }

    public static void handleButtonInteraction(ButtonInteractionEvent event) {
        long messageId = event.getMessage().getIdLong();
        int currentPage = culturePageMap.getOrDefault(messageId, 1);
        int newPage = event.getComponentId().equals("char_next") ? currentPage + 1 : currentPage - 1;

        if (newPage < 1 || newPage > TOTAL_PAGES) return;

        culturePageMap.put(messageId, newPage);
        EmbedBuilder updatedEmbed = new CharacterCreation().getCharPage(newPage);

        event.editMessageEmbeds(updatedEmbed.build())
                .setActionRow(
                        Button.primary("char_prev", "◀️ Previous").withDisabled(newPage == 1),
                        Button.primary("char_next", "Next ▶️").withDisabled(newPage == TOTAL_PAGES)
                )
                .queue();
    }
}
