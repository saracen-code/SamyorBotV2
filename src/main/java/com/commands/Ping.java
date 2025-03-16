package com.commands;

import com.commands.setup.CommandBase;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping extends CommandBase {
    @Override
    public void execute(MessageReceivedEvent event) {event.getChannel().sendMessage("Pong!").queue();
    }
    @Override
    public void execute(SlashCommandInteractionEvent event) {event.getChannel().sendMessage("Pong!").queue();
    }
}
