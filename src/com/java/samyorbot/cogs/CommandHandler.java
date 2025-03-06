package com.java.samyorbot.cogs;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class CommandHandler extends ListenerAdapter {
    public abstract void onCommand(MessageReceivedEvent event, String command);
}