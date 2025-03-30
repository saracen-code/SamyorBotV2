package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.List;

public class ScienceInterface implements ICommand {
    public static Color interfaceColor = Color.decode("#777311");

    @Override
    public String getName() {
        return "science";
    }

    @Override
    public String getDescription() {
        return "Manages scholarly circles, research, discoveries, and advancements.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        sendScienceMenu(event.getChannel());
    }

    public void sendScienceMenu(MessageChannel channel) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("**❮ The Scholar Circle ❯**")
                .setDescription("Welcome to the Scholar Menu!\n\n")
                .setFooter("Good luck on your travels!");

        channel.sendMessageEmbeds(embed.build())
                .queue();

    }

}
