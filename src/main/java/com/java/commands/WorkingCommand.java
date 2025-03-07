package com.java.commands;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileNotFoundException;

public interface WorkingCommand {
    void execute(MessageReceivedEvent event) throws FileNotFoundException;
}
