package com.java.commands;

import com.java.saskartan.PhonologyBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class PhoMo implements WorkingCommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().trim();  // Trim whitespace around the message
        String[] commandParts = message.split(" ", 2);  // Split only on the first space (if there is one)
        String input = commandParts[1].toLowerCase();  // Get input in lowercase
        String saskartanizedInput = PhonologyBot.applyPhonology(input);
        event.getChannel().sendMessage("Your word in Saskartanized format becomes " + saskartanizedInput).queue();
    }
}
