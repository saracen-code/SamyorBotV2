package com;

import com.commands.CommandHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
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
            JDA jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT) // Enable message content intent
                    .setActivity(Activity.playing("Listening to $ commands")) // Bot's status
                    .build();

            // Wait until JDA is ready before adding listeners
            jda.awaitReady();

            // Register CommandHandler with the JDA instance
            jda.addEventListener(new CommandHandler(jda));

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
}
