package com.samyorBot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samyorBot.classes.Player;
import com.samyorBot.commands.embeds.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.File;
import java.io.IOException;

import com.samyorBot.commands.*;

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

        JDA jda = null;
        try {

            System.out.println("🚀 Connecting to Discord...");
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT) // Enable message content intent
                    .setActivity(Activity.playing("Listening to ! commands")) // Bot's status
                    .build();

            // Register CommandHandler with the JDA instance
            jda.addEventListener(new Listeners());
            CommandManager manager = new CommandManager();
            // list commands
            manager.add(new Help());
            manager.add(new PhoMo());
            manager.add(new Ping());
            manager.add(new CharacterInterface());
            manager.add(new CharacterCreation());
            manager.add(new CountryInterface());
            manager.add(new CityInterface());
            manager.add(new DiplomacyInterface());
            manager.add(new InvestmentInterface());
            manager.add(new MarketInterface());
            manager.add(new ScienceInterface());
            manager.add(new TaxationInterface());
            manager.add(new TravelInterface());
            jda.addEventListener(manager);

            // load characters
            Player.charInitializer();

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
