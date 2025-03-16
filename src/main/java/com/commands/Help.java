package com.commands;

import com.commands.setup.CommandBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Help extends CommandBase {
    public static final Color commandColor = Color.decode("#777311");
    private static final Map<Long, Integer> messagePageMap = new HashMap<>();
    private static final int TOTAL_PAGES = 3;

    @Override
    public void execute(MessageReceivedEvent event) {
        sendHelp(event.getChannel());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
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
                    message.addReaction(Emoji.fromUnicode("◀️")).queue();
                    message.addReaction(Emoji.fromUnicode("▶️")).queue();
                    messagePageMap.put(message.getIdLong(), 1);
                });
    }

    private EmbedBuilder getHelpPage(int page) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(commandColor);
        embed.setFooter("Use ◀️/▶️ reactions or buttons to navigate.");

        switch (page) {
            case 1:
                embed.setTitle("❂ **Samyor's Commands 1.0** ❂")
                        .setDescription("Welcome, traveler. Choose wisely.")
                        .addField("__UTILITIES COMMANDS__", "", false)
                        .addField("!help", "Displays this help menu.", false)
                        .addField("!ping", "🚧 Check bot response time.", false)
                        .addField("PAGE", "1/3", false);
                break;
            case 2:
                embed.setTitle("❂ **Samyor's Commands 1.0** ❂")
                        .setDescription("Startup commands for beginners.")
                        .addField("__STARTUP COMMANDS__", "", false)
                        .addField("**!setup**", "🚧 Start your journey.", false)
                        .addField("**!tutorials**", "🚧 Learn bot features.", false)
                        .addField("PAGE", "2/3", false);
                break;
            case 3:
                embed.setTitle("❂ **Samyor's Commands 1.0** ❂")
                        .setDescription("More commands coming soon!")
                        .addField("**Upcoming Features**", "Stay tuned!", false)
                        .addField("PAGE", "3/3", false);
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

    public static void handleReaction(MessageReactionAddEvent event) {
        if (!event.getUser().isBot()) {
            long messageId = event.getMessageIdLong();
            int currentPage = messagePageMap.getOrDefault(messageId, 1);
            int newPage = event.getReaction().getEmoji().getName().equals("▶️") ? currentPage + 1 : currentPage - 1;

            if (newPage < 1 || newPage > TOTAL_PAGES) return;

            messagePageMap.put(messageId, newPage);
            event.getChannel().retrieveMessageById(messageId).queue(message ->
                    message.editMessageEmbeds(new Help().getHelpPage(newPage).build()).queue()
            );

            event.getReaction().removeReaction(event.getUser()).queue();
        }
    }
}
