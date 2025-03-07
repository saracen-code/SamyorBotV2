package com.java.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping implements WorkingCommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong!").queue();
    }
}
