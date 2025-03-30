package com.samyorBot.commands;

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

public class Help implements ICommand {
    public static final Color commandColor = Color.decode("#777311");
    private static final Map<Long, Integer> messagePageMap = new HashMap<>();
    private static final int TOTAL_PAGES = 3;

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Provides information and guidance on available commands and their usage.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("");
        sendHelp(event.getChannel());
    }

    private void sendHelp(MessageChannel channel) {
        EmbedBuilder embed = getHelpPage(1);
        channel.sendMessageEmbeds(embed.build())
                .setActionRow(
                        Button.primary("help_prev", "◀️ Previous").asDisabled(),
                        Button.primary("help_next", "Next ▶️")
                )
                .queue(message -> {
                    messagePageMap.put(message.getIdLong(), 1);
                });
    }

    private EmbedBuilder getHelpPage(int page) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(commandColor);
        embed.setFooter("Use ◀️/▶️ buttons to navigate.");

        switch (page) {
            case 1:
                embed.setTitle("❂ **Samyor's Commands 1.0** ❂")
                        .setDescription("Welcome, traveler. Choose wisely.")
                        .addField("__UTILITIES COMMANDS__", "", false)
                        .addField("!help", "Displays this help menu.", false)
                        .addField("!ping", "🚧 Check bot response time.", false)
                        .addField("PAGE", "1/3", false);
                FunFacts.addFunFacts(embed);
                break;
            case 2:
                embed.setTitle("❂ **Samyor's Commands 1.0** ❂")
                        .setDescription("Startup commands for beginners.")
                        .addField("__STARTUP COMMANDS__", "", false)
                        .addField("**!setup**", "🚧 Start your journey.", false)
                        .addField("**!tutorials**", "🚧 Learn bot features.", false)
                        .addField("PAGE", "2/3", false);
                FunFacts.addFunFacts(embed);
                break;
            case 3:
                embed.setTitle("❂ **Samyor's Commands 1.0** ❂")
                        .setDescription("More commands coming soon!")
                        .addField("**Upcoming Features**", "Stay tuned!", false)
                        .addField("PAGE", "3/3", false);
                FunFacts.addFunFacts(embed);
                break;
        }
        return embed;
    }

    public static void handleButtonInteraction(ButtonInteractionEvent event) {
        long messageId = event.getMessage().getIdLong();
        int currentPage = messagePageMap.getOrDefault(messageId, 1);
        int newPage = event.getComponentId().equals("help_next") ? currentPage + 1 : currentPage - 1;

        if (newPage < 1 || newPage > TOTAL_PAGES) return;

        messagePageMap.put(messageId, newPage);
        EmbedBuilder updatedEmbed = new Help().getHelpPage(newPage);

        event.editMessageEmbeds(updatedEmbed.build())
                .setActionRow(
                        Button.primary("help_prev", "◀️ Previous").withDisabled(newPage == 1),
                        Button.primary("help_next", "Next ▶️").withDisabled(newPage == TOTAL_PAGES)
                )
                .queue();
    }
}
