package com.java.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Help implements WorkingCommand {

    @Override
    public void execute(MessageReceivedEvent event) throws FileNotFoundException {

        // Creating the Embed
        EmbedBuilder embed = new EmbedBuilder();

        // Set the title of the embed to give it a medieval feel
        embed.setTitle("❂ **Samyor's Commands 1.0** ❂")  // Optional: add an icon URL for better look
                .setDescription("Welcome, traveler. Below are the available commands of SamyorBot. Choose wisely, as each serves a unique purpose ($tutorial for more help).")
                .setColor(new Color(255, 215, 0));  // Gold color for the header
        // Add fields with commands and descriptions
        embed.addField("**$help**", "Displays this help menu with available commands.", false)
                .addField("**$ping**", "Check the bot's response time.", false)
                .addField("**$taxation**", "Open the tax management menu (Kings only).", false);

        FunFacts.addFunFacts(embed);

        // Add a longer footer with more immersive medieval flavor
        embed.setFooter("The Eztadād await your commands... Explore, conquer, and lead the way!");

        // Set a medieval-themed color (gold, for instance)
        embed.setColor(new Color(255, 215, 0));  // Gold color

        // Send the embed
        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }
}
