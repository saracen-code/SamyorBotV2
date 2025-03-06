package com.java;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Main extends ListenerAdapter {

    public static void main(String[] args) {
        String token = getBotToken("config.json");
        if (token == null) {
            System.err.println("Failed to load bot token. Exiting...");
            return;
        }

        try {
            JDABuilder.createDefault(token)
                    .setActivity(Activity.playing("Listening to $ commands"))
                    .addEventListeners(new Main())
                    .build();
        } catch (Exception e) {  // ✅ Catch generic Exception if needed
            e.printStackTrace();
        }

    }

    public static String getBotToken(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            return jsonNode.get("token").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return; // Ignore bot messages

        String message = event.getMessage().getContentRaw();
        if (message.startsWith("$")) {
            String command = message.substring(1).toLowerCase(); // Remove "$" and make lowercase
            switch (command) {
                case "ping":
                    event.getChannel().sendMessage("Pong!").queue();
                    break;
                case "hello":
                    event.getChannel().sendMessage("Hello, " + event.getAuthor().getAsMention() + "!").queue();
                    break;
                default:
                    event.getChannel().sendMessage("Unknown command. Try `$ping` or `$hello`.").queue();
            }
        }
    }
}
