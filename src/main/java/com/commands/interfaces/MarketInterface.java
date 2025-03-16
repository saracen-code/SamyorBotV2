package com.commands.interfaces;

import com.commands.setup.CommandBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.Color;
import java.util.Collection;
import java.util.Random;

public class MarketInterface extends CommandBase {
    private static final Random random = new Random();

    @Override
    public void execute(MessageReceivedEvent event) {
        enterMarket(event.getChannel());
    }
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        enterMarket(event.getChannel());
    }

    public void enterMarket(MessageChannel channel) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("**❮ The Market of Ghallab ❯**")
                .setDescription("You step into the sprawling, chaotic bazaar of Ghallab. Merchants cry out their wares, exotic spices fill the air, and the chatter of countless traders surrounds you. Every alley offers a new mystery, a new deal, or perhaps... a new danger.")
                .setColor(Color.ORANGE);

        channel.sendMessageEmbeds(embed.build())
                .setActionRow(
                        Button.primary("market_deeper", "Venture Deeper"),
                        Button.secondary("market_crowd", "Follow the Crowds"),
                        Button.success("market_deals", "Seek the Best Deals"),
                        Button.danger("market_exit", "Leave the Market")
                )
                .queue();
    }

    public static void buttonHandler(ButtonInteractionEvent event) {
        switch (event.getButton().getId()) {
            case "market_deeper":
                event.replyEmbeds(getRandomEvent()).addActionRow(Button.primary("market_continue", "Continue Exploring"),
                        Button.danger("market_exit", "Leave the Market")).queue();
                break;
            case "market_crowd":
                event.replyEmbeds(getRandomEvent()).addActionRow(Button.primary("market_continue", "Continue Exploring"),
                        Button.danger("market_exit", "Leave the Market")).queue();
                break;
            case "market_deals":
                event.replyEmbeds(getDealEvent()).addActionRow(Button.primary("market_continue", "Continue Exploring"),
                        Button.danger("market_exit", "Leave the Market")).queue();
                break;
            case "market_exit":
                event.reply("You leave the market, the echoes of trade and chatter fading behind you.").queue();
                break;
            case "market_continue":
                event.replyEmbeds(getRandomEvent()).addActionRow(Button.primary("market_continue", "Continue Exploring"),
                        Button.danger("market_exit", "Leave the Market")).queue();
                break;
        }
    }

    private static Collection<? extends MessageEmbed> getRandomEvent() {
        String[] events = {
                "A merchant grabs your sleeve, offering rare silks from the East. Do you stop to bargain?",
                "A fight breaks out over the price of war camels, the crowd forming a circle around the combatants.",
                "The scent of fresh bread leads you to a baker who claims to sell the softest loaves in Ghallab.",
                "An old storyteller weaves tales of lost caravans and hidden treasures. Do you stay to listen?",
                "A shifty-eyed man whispers of a deal too good to be true. Do you risk it?"
        };
        return (Collection<? extends MessageEmbed>) new EmbedBuilder().setDescription(events[random.nextInt(events.length)]).setColor(Color.YELLOW);
    }

    private static Collection<? extends MessageEmbed> getDealEvent() {
        return (Collection<? extends MessageEmbed>) new EmbedBuilder().setDescription("With a keen eye, you spot the best deals in the market: \n - War Horse: 50 Gold\n - Olive Oil: 10 Gold\n - Fine Silk: 30 Gold").setColor(Color.GREEN);
    }
}
