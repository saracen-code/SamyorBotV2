package samyorbot.cogs;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class Fun extends ListenerAdapter {

    // Responds to a command !ping
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        TextChannel channel = event.getTextChannel();

        // Avoid responding to bot messages
        if (message.getAuthor().isBot()) return;

        // Command: !ping
        if (message.getContentRaw().equalsIgnoreCase("!ping")) {
            channel.sendMessage("Pong!").queue();
        }

        // Command: !roll (simulates rolling a 6-sided die)
        if (message.getContentRaw().equalsIgnoreCase("!roll")) {
            int roll = (int)(Math.random() * 6) + 1;
            channel.sendMessage("You rolled a " + roll).queue();
        }

        // Command: !joke (random joke)
        if (message.getContentRaw().equalsIgnoreCase("!joke")) {
            channel.sendMessage("Why don't skeletons fight each other? They don't have the guts!").queue();
        }
    }
}
