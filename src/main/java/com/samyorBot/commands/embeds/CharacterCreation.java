package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import com.samyorBot.data.FunFacts;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
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
        handleButtonInteraction(event);
    }

    private void sendCharCreation(MessageChannel channel) {
        EmbedBuilder embed = getCharPage(1);
        channel.sendMessageEmbeds(embed.build())
                .setActionRow(
                        Button.primary("char_prev", "◀️ Previous").asDisabled(),
                        Button.primary("char_next", "Next ▶️"),
                        Button.success("char_confirm", "✅ Confirm").asDisabled()
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
                        .addField("The Ehkiyas", "Lorem Ipsum Dolor et Simet.", false);
                FunFacts.addFunFacts(embed);
                break;
            case 2:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 2: NAME__", "", false)
                        .addField("Randomize Sask", "Obtain a random name by culture.", false);
                FunFacts.addFunFacts(embed);
                break;
            case 3:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 3: START LOCATION__", "", false);
                FunFacts.addFunFacts(embed);
                break;
            case 4:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 4: BIRTH DATE__", "select", false);
                FunFacts.addFunFacts(embed);
                break;
            case 5:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 5: DESCRIPTION (OPTIONAL)__", "", false);
                FunFacts.addFunFacts(embed);
                break;
            case 6:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 6: SKILL DESCRIPTION__", "", false);
                FunFacts.addFunFacts(embed);
                break;
            case 7:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 7: DESCRIPTION (OPTIONAL)__", "", false);
                FunFacts.addFunFacts(embed);
                break;
            case 8:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 8: PORTRAIT (OPTIONAL)__", "", false);
                FunFacts.addFunFacts(embed);
                break;
            case 9:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("__PART 9: FAMILY__", "", false);
                FunFacts.addFunFacts(embed);
                break;
            case 10:
                embed.setTitle("❂ **Samyor's Character Creator 1.0** ❂")
                        .addField("CONFIRM", "Confirm the creation of your character.", false);
                FunFacts.addFunFacts(embed);
                break;
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

        event.deferEdit().queue(); // Ensures interaction is acknowledged

        event.getMessage().editMessageEmbeds(updatedEmbed.build())
                .setActionRow(
                        Button.primary("char_prev", "◀️ Previous").withDisabled(newPage == 1),
                        Button.primary("char_next", "Next ▶️").withDisabled(newPage == TOTAL_PAGES),
                        Button.success("char_confirm", "✅ Confirm").withDisabled(newPage != TOTAL_PAGES)
                ).queue();
    }
}

