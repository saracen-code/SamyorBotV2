package com.java.commands;

import com.java.commands.taxation.Taxation;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler extends ListenerAdapter {

    private Map<String, WorkingCommand> commands;

    public CommandHandler() {
        commands = new HashMap<>();
        // Register commands
        commands.put("$help", new Help());
        commands.put("$ping", new Ping());
        commands.put("$taxation", new Taxation());
        commands.put("$diplomacy", new DiplomacyInterface());
        // You can register more commands here...
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().trim();  // Trim whitespace around the message
        if (message.startsWith("$")) {  // Check if the message starts with the command prefix
            String[] commandParts = message.split(" ", 2);  // Split only on the first space (if there is one)
            String command = commandParts[0].toLowerCase();  // Get command in lowercase

            // Handle commands by calling their execute method
            WorkingCommand cmd = commands.get(command);
            if (cmd != null) {
                try {
                    cmd.execute(event);
                } catch (Exception e) {
                    e.printStackTrace();
                    event.getChannel().sendMessage("Error executing the command: " + e.getMessage()).queue();
                }
            } else {
                event.getChannel().sendMessage("Unknown command: " + command).queue();
            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        // Handle button interactions globally here
        String buttonId = event.getButton().getId();
        System.out.println("👉 Button pressed! ID:" + buttonId);

        Taxation taxation;
        DiplomacyInterface diplomacy;
        // Handle button actions based on the button ID
        switch (buttonId.substring(0, 4)) {
            case "t.ta":
                // Redirect button events for taxation to Taxation handler
                taxation = new Taxation();
                taxation.handleButtonClick(event);  // Handle taxation buttons
                break;
            case "t.in":
            case "t.de":
                // Handle the increment/decrement buttons
                taxation = new Taxation();
                taxation.handleIncrementButtons(event);
                break;
            case "d.se":
                diplomacy = new DiplomacyInterface();
                diplomacy.buttonHandler(event);
                break;
            case "d.vi":
                diplomacy = new DiplomacyInterface();
                diplomacy.buttonHandler(event);
            default:
                System.out.println(event.getButton().getId());
                event.reply("Unknown button action!").setEphemeral(true).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        // Handle modal interactions globally here
        String modalId = event.getModalId();
        System.out.println("👉 Modal submitted! ID:" + modalId);

        Taxation taxation;
        DiplomacyInterface diplomacy;

        // Handle modal actions based on the modal ID
        switch (modalId.substring(0, 4)) {
            case "s.co":
                diplomacy = new DiplomacyInterface();
                diplomacy.modalHandler(event);  // Handle diplomacy modals
                break;
            default:
                System.out.println("Unknown modal ID: " + modalId);
                event.reply("Unknown modal action!").setEphemeral(true).queue();
        }
    }
}
