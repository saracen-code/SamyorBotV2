package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import com.samyorBot.data.FunFacts;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.Color;
import java.util.List;

public class CityInterface implements ICommand {
    public static Color interfaceColor = Color.decode("#5b2d1a");

    @Override
    public String getName() {
        return "city";
    }

    @Override
    public String getDescription() {
        return "Local city information, events and actions.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.replyEmbeds(sendCityIntroPage()).addActionRow(
                        Button.secondary("city_tutorial", "Tutorial"),
                        Button.secondary("city_demographics", "Demographics"),
                        Button.secondary("city_status", "Status"),
                        Button.primary("city_enter", "Enter the City"))
                .setEphemeral(true)
                .queue();
    }

    public static void buttonHandler(ButtonInteractionEvent event) {
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();
        String buttonId = event.getComponentId();

        switch (buttonId) {
            case "city_tutorial":
                channel.retrieveMessageById(messageId).queue(message -> {
                    message.editMessageEmbeds(sendCityTutorial()).queue();
                });
                break;
            case "city_demographics":
                channel.retrieveMessageById(messageId).queue(message -> {
                    message.editMessageEmbeds(sendCityDemographics()).queue();
                });
                break;
            case "city_status":
                channel.retrieveMessageById(messageId).queue(message -> {
                    message.editMessageEmbeds(sendCityStatus()).queue();
                });
                break;
            case "city_enter":
                channel.retrieveMessageById(messageId).queue(message -> {
                    message.editMessageEmbeds(sendEnterCityPage()).queue();
                });
                break;
            default:
                System.out.println("Failed to retrieve message.");
        }

        // Remove the button interaction after processing (optional)
        event.deferEdit().queue();
    }

    public static MessageEmbed sendCityIntroPage() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Welcome to the City!")
                .setDescription("Welcome to the heart of the medieval world. Here, you will build, manage, and grow a thriving city.")
                .setFooter("May your city flourish!");
        FunFacts.addFunFacts(embed);

        return embed.build();
    }

    public static MessageEmbed sendCityTutorial() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("City Tutorial")
                .setDescription("Here’s how the city system works:\n\n"
                        + "1. **City Demographics**: Learn about the population and development.\n"
                        + "2. **City Status**: Check the current condition and resources of the city.\n"
                        + "3. **Entering the City**: Buy city specialties, set up workshops, and more!")
                .setFooter("The city needs a strong leader like you!");

        return embed.build();
    }

    public static MessageEmbed sendCityDemographics() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("City Demographics")
                .setDescription("Here are the key demographic details of your city:")
                .addField("Population", "5000", true)
                .addField("Tax Rate", "10%", true)
                .addField("Economic Status", "Stable", true)
                .addField("Development Level", "Growing Town (Tier 2)", true)
                .setFooter("The city is growing, but there are challenges ahead!");

        return embed.build();
    }

    public static MessageEmbed sendCityStatus() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("City Status")
                .setDescription("Here's the current status of your city:")
                .addField("Resources", "Food: Adequate, Gold: Low, Wood: High", false)
                .addField("Infrastructure", "Roads: Poor, Defense: Average, Health: Good", false)
                .addField("Overall Status", "Stable, but needs attention to defense and infrastructure.", false)
                .setFooter("Manage wisely to keep the city prosperous!");

        return embed.build();
    }

    public static MessageEmbed sendEnterCityPage() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor)
                .setTitle("Enter the City")
                .setDescription("You’ve entered the city gates. Here are your options:\n\n"
                        + "1. **Buy City Specialty**: Purchase unique items and resources.\n"
                        + "2. **Set Up a Guild**: Establish a guild to recruit followers and influence the city.\n"
                        + "3. **Set Up a Workshop**: Start crafting and producing goods.\n"
                        + "4. **Open a Trade Booth**: Speak with the mayor to set up a trade booth and engage in commerce.")
                .setFooter("The city is at your fingertips. What will you do?");
        return embed.build();
    }
}