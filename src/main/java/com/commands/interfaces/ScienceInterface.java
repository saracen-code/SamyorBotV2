package com.commands.interfaces;

import com.commands.setup.CommandBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class ScienceInterface extends CommandBase {
    public static Color interfaceColor = Color.decode("#777311");

    @Override
    public void execute(MessageReceivedEvent event) {
        sendScienceMenu(event.getChannel());
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
