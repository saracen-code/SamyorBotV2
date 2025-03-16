package com.commands;

import com.commands.setup.CommandBase;

import com.saskartan.PhonologyBot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class PhoMo extends com.commands.setup.CommandBase {
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String message = Objects.requireNonNull(event.getOption("input")).getAsString();
        String[] commandParts = message.split(" ", 2);  // Split only on the first space (if there is one)
        String input = commandParts[0].toLowerCase();  // Get input in lowercase
        String saskartanizedInput = PhonologyBot.applyPhonology(input);
        event.getChannel().sendMessage("Your word in Saskartanized format becomes " + saskartanizedInput).queue();
    }
    @Override
    public void execute(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().trim();  // Trim whitespace around the message
        String[] commandParts = message.split(" ", 2);  // Split only on the first space (if there is one)
        String input = commandParts[1].toLowerCase();  // Get input in lowercase
        String saskartanizedInput = PhonologyBot.applyPhonology(input);
        event.getChannel().sendMessage("Your word in Saskartanized format becomes " + saskartanizedInput).queue();
    }
}
