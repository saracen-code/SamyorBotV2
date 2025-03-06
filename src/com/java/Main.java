package com.java;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.File;
import java.io.IOException;

public class Main extends ListenerAdapter {

    public static void main(String[] args) {
        System.out.println("🔄 Starting the bot...");

        // Load token from config.json
        String token = getBotToken("config.json");
        if (token == null) {
            System.err.println("❌ Failed to load bot token. Exiting...");
            return;
        }
        System.out.println("✅ Bot token loaded successfully!");

        try {
            System.out.println("🚀 Connecting to Discord...");
            JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT) // Enable message content intent
                    .setActivity(Activity.playing("Listening to $ commands")) // Bot's status
                    .addEventListeners(new Main()) // Register the event listener
                    .build();
            System.out.println("✅ Bot is online and listening for messages!");
        } catch (Exception e) {
            System.err.println("❌ Error while starting the bot:");
            e.printStackTrace();
        }
    }

    // Function to read the bot token from config.json
    public static String getBotToken(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            return jsonNode.get("token").asText();
        } catch (IOException e) {
            System.err.println("❌ Error reading config.json:");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Ignore bot messages
        if (event.getAuthor().isBot()) return;

        // Get the message content
        String message = event.getMessage().getContentRaw();
        System.out.println("📩 Received message: " + message); // Log the message for debugging

        // Check if the message starts with '$'
        if (message.startsWith("$")) {
            String command = message.substring(1).toLowerCase();  // Remove '$' and convert to lowercase
            System.out.println("🔹 Command detected: " + command); // Log the command

            // Command handling
            switch (command) {
                case "ping":
                    event.getChannel().sendMessage("Pong!").queue();
                    System.out.println("✅ Responded with: Pong!");
                    break;
                case "hello":
                    event.getChannel().sendMessage("Hello, " + event.getAuthor().getAsMention() + "!").queue();
                    System.out.println("✅ Responded with: Hello, " + event.getAuthor().getName());
                    break;
                default:
                    event.getChannel().sendMessage("Unknown command. Try `$ping` or `$hello`.").queue();
                    System.out.println("⚠️ Unknown command: " + command);
            }
        }
    }
}
