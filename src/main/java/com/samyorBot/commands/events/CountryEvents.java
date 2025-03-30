package com.samyorBot.commands.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;

public class CountryEvents {
    /// COASTAL CITY EVENTS
    public static void piratesRaidDocks(ButtonInteractionEvent event, int playerReputation) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("🏴‍☠️ Pirates Raid the Docks!")
                .setDescription("The pirates have raided the docks, causing a temporary loss to the shipyards and trade posts. The coastal defenses were insufficient.");

        // Options and consequences
        String consequences = "1. **Rebuild quickly** with a significant investment of resources. (+15 Gold, Reputation decreases by -5 due to inconvenience to locals)\n" +
                "2. **Invest in naval defenses** to prevent future raids (Invest Gold into naval fortifications). (+5 Reputation, Risk of further raids reduced)\n" +
                "3. **Negotiate with pirates**. (Risky: Could anger the populace, but costs only 50 Gold)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("Make your choice carefully, the safety of your city depends on it!");

        // Buttons for choices
        Button rebuildButton = Button.primary("rebuild_docks", "Rebuild the Docks");
        Button investDefensesButton = Button.primary("invest_defenses", "Invest in Naval Defenses");
        Button negotiatePiratesButton = Button.danger("negotiate_pirates", "Negotiate with Pirates");

        event.replyEmbeds(embed.build())
                .addActionRow(rebuildButton, investDefensesButton, negotiatePiratesButton)
                .queue();
    }

    public static void stormWrecksHarbor(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.CYAN)
                .setTitle("🌊 Storm Wrecks the Harbor!")
                .setDescription("A terrible storm has wreaked havoc on the harbor. Ships have been damaged, and the warehouses flooded. Trade is halted until repairs can be completed.");

        String consequences = "1. **Repair the harbor** by investing Gold and labor. (Cost: 200 Gold, +10 Reputation)\n" +
                "2. **Invest in coastal fortifications** to minimize storm damage in the future. (Cost: 300 Gold, +5 Reputation)\n" +
                "3. **Ignore the damage for now** and focus on other parts of the economy. (-5 Reputation, Short-term losses in trade)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("Your decisions here will impact your city’s trade economy.");

        // Buttons for choices
        Button repairButton = Button.primary("repair_harbor", "Repair the Harbor");
        Button investFortificationsButton = Button.primary("invest_fortifications", "Invest in Coastal Fortifications");
        Button ignoreDamageButton = Button.danger("ignore_damage", "Ignore the Damage");

        event.replyEmbeds(embed.build())
                .addActionRow(repairButton, investFortificationsButton, ignoreDamageButton)
                .queue();
    }

    public static void merchantFleetBringsGoods(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("🚢 Merchant Fleet Brings Exotic Goods!")
                .setDescription("A fleet of merchants from distant lands has arrived, bringing with them exotic goods. This is a golden opportunity for your city to profit from trade.");

        String consequences = "1. **Invest in new warehouses** to maximize the influx of goods and profits. (Cost: 150 Gold, +10 Reputation)\n" +
                "2. **Build a market district** to increase the distribution of goods. (Cost: 200 Gold, +8 Reputation, +5 Gold per turn)\n" +
                "3. **Focus on trade routes** and let the merchant fleet handle distribution. (Cost: 50 Gold, short-term gain of 100 Gold)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("Your choices here could elevate your city’s wealth significantly!");

        // Buttons for choices
        Button investWarehouseButton = Button.primary("invest_warehouse", "Invest in New Warehouses");
        Button buildMarketButton = Button.primary("build_market", "Build a Market District");
        Button focusTradeButton = Button.danger("focus_trade", "Focus on Trade Routes");

        event.replyEmbeds(embed.build())
                .addActionRow(investWarehouseButton, buildMarketButton, focusTradeButton)
                .queue();
    }

    ///  DESERT AND OASES CITY EVENTS
    public static void droughtDevastatesCrops(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.YELLOW)
                .setTitle("☀️ Drought Devastates Crops!")
                .setDescription("A severe drought has struck your city’s farmlands, devastating crop yields. You need to act quickly to prevent further losses.");

        String consequences = "1. **Invest in aqueducts** to provide water to the farmlands. (Cost: 300 Gold, +5 Reputation)\n" +
                "2. **Diversify crops** to reduce reliance on water-intensive plants. (Cost: 100 Gold, +3 Reputation, long-term gain)\n" +
                "3. **Do nothing** and save resources. (Risk: Further crop failure, -10 Reputation)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("Act quickly, the livelihoods of your citizens depend on it!");

        // Buttons for choices
        Button investAqueductsButton = Button.primary("invest_aqueducts", "Invest in Aqueducts");
        Button diversifyCropsButton = Button.primary("diversify_crops", "Diversify Crops");
        Button doNothingButton = Button.danger("do_nothing", "Do Nothing");

        event.replyEmbeds(embed.build())
                .addActionRow(investAqueductsButton, diversifyCropsButton, doNothingButton)
                .queue();
    }

    public static void silkRoadCaravanArrives(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTitle("🐫 Silk Road Caravan Arrives!")
                .setDescription("A Silk Road caravan has arrived with luxury goods. Trade is booming, and your city has the opportunity to profit from the influx of rare items.");

        String consequences = "1. **Build a caravanserai** to house the caravans and improve trade. (Cost: 200 Gold, +15 Reputation)\n" +
                "2. **Expand the trade route** to bring more goods into your city. (Cost: 300 Gold, +10 Reputation)\n" +
                "3. **Let the caravan go** without intervention. (Short-term gain of 50 Gold, -5 Reputation)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("This is a rare opportunity to grow your city’s wealth.");

        // Buttons for choices
        Button buildCaravanseraiButton = Button.primary("build_caravanserai", "Build a Caravanserai");
        Button expandTradeRouteButton = Button.primary("expand_trade_route", "Expand the Trade Route");
        Button letCaravanGoButton = Button.danger("let_caravan_go", "Let the Caravan Go");

        event.replyEmbeds(embed.build())
                .addActionRow(buildCaravanseraiButton, expandTradeRouteButton, letCaravanGoButton)
                .queue();
    }

    /// LUMBER EVENTS

    public static void lumberjackStrike(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.BLUE)
                .setTitle("🪵 Lumberjack Strike!")
                .setDescription("The lumberjacks have gone on strike, causing wood prices to skyrocket. This increase in building costs will strain your resources.");

        String consequences = "1. **Invest in better working conditions** to end the strike. (Cost: 100 Gold, +5 Reputation)\n" +
                "2. **Wait out the strike** and focus on other industries. (No cost, but higher future building costs)\n" +
                "3. **Break the strike** forcefully. (Cost: 50 Gold, -10 Reputation, short-term wood price drop)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("This decision could have long-lasting effects on your city’s economy.");

        // Buttons for choices
        Button investWorkingConditionsButton = Button.primary("invest_working_conditions", "Invest in Working Conditions");
        Button waitStrikeButton = Button.primary("wait_strike", "Wait Out the Strike");
        Button breakStrikeButton = Button.danger("break_strike", "Break the Strike");

        event.replyEmbeds(embed.build())
                .addActionRow(investWorkingConditionsButton, waitStrikeButton, breakStrikeButton)
                .queue();
    }

    ///  CITY EVENTS

    public static void royalCourtRequestsPalace(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.orange)
                .setTitle("👑 Royal Court Requests a Grand Palace Wing!")
                .setDescription("The royal court demands a grand addition to the palace. This expensive project will boost the prestige of the city, but at a high cost.");

        String consequences = "1. **Accept the request** and invest in the grand palace wing. (Cost: 500 Gold, +20 Reputation, +5 Prestige)\n" +
                "2. **Refuse the request** and save your resources. (Cost: 50 Gold in lost prestige, -5 Reputation)\n" +
                "3. **Negotiate with the royal court** for a more reasonable cost. (Risky: Could increase tensions, -5 Reputation, potential 100 Gold cost)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("The future of your capital may be shaped by this decision!");

        // Buttons for choices
        Button acceptRequestButton = Button.primary("accept_request", "Accept the Request");
        Button refuseRequestButton = Button.danger("refuse_request", "Refuse the Request");
        Button negotiateButton = Button.primary("negotiate_royal", "Negotiate with the Court");

        event.replyEmbeds(embed.build())
                .addActionRow(acceptRequestButton, refuseRequestButton, negotiateButton)
                .queue();
    }

    public static void overcrowdingCausesUnrest(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.red)
                .setTitle("🏚️ Overcrowding Causes Unrest!")
                .setDescription("The population has swelled beyond capacity, and overcrowding is now leading to unrest. Crime rates are rising, and public order is in jeopardy.");

        String consequences = "1. **Invest in tenements** to quickly expand housing. (Cost: 300 Gold, +10 Reputation, reduces unrest)\n" +
                "2. **Build public works projects** to improve the city’s infrastructure and morale. (Cost: 250 Gold, +5 Reputation, lowers unrest)\n" +
                "3. **Ignore the issue for now** and save resources. (Risk: -10 Reputation, -5 Prestige, increased unrest)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("Your choice could calm the unrest—or make it worse!");

        // Buttons for choices
        Button investTenementsButton = Button.primary("invest_tenements", "Invest in Tenements");
        Button buildPublicWorksButton = Button.primary("build_public_works", "Build Public Works");
        Button ignoreIssueButton = Button.danger("ignore_issue", "Ignore the Issue");

        event.replyEmbeds(embed.build())
                .addActionRow(investTenementsButton, buildPublicWorksButton, ignoreIssueButton)
                .queue();
    }

    public static void greatFestival(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.magenta)
                .setTitle("🎉 The Great Festival of the City!")
                .setDescription("The city is planning a grand festival to celebrate its prosperity. The festival will boost morale and public loyalty, but the costs could be high.");

        String consequences = "1. **Fund the festival** with a generous investment. (Cost: 400 Gold, +10 Reputation, +15 Public Loyalty)\n" +
                "2. **Hold a modest festival** to balance the costs. (Cost: 150 Gold, +5 Reputation, +5 Public Loyalty)\n" +
                "3. **Cancel the festival** due to budget constraints. (Risk: -10 Reputation, -5 Public Loyalty, increased tension)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("The city’s mood could shift greatly depending on your decision.");

        // Buttons for choices
        Button fundFestivalButton = Button.primary("fund_festival", "Fund the Festival");
        Button modestFestivalButton = Button.primary("modest_festival", "Hold a Modest Festival");
        Button cancelFestivalButton = Button.danger("cancel_festival", "Cancel the Festival");

        event.replyEmbeds(embed.build())
                .addActionRow(fundFestivalButton, modestFestivalButton, cancelFestivalButton)
                .queue();
    }

    public static void politicalCorruptionScandal(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("🤐 Political Corruption Scandal!")
                .setDescription("A major scandal has rocked the city’s political elite. Corruption within the highest ranks has been exposed, and the public demands swift action.");

        String consequences = "1. **Purge the corrupt officials** to restore order and public trust. (Cost: 300 Gold, +10 Reputation, -5 Public Loyalty)\n" +
                "2. **Investigate the corruption** and promise reforms. (Cost: 200 Gold, +5 Reputation, +5 Public Loyalty, slower impact)\n" +
                "3. **Cover up the scandal** to avoid further instability. (Risk: -10 Reputation, -20 Public Loyalty, future risks of unrest)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("This scandal could shape your reputation forever.");

        // Buttons for choices
        Button purgeOfficialsButton = Button.primary("purge_officials", "Purge the Corrupt Officials");
        Button investigateButton = Button.primary("investigate_corruption", "Investigate and Promise Reforms");
        Button coverUpButton = Button.danger("cover_up", "Cover Up the Scandal");

        event.replyEmbeds(embed.build())
                .addActionRow(purgeOfficialsButton, investigateButton, coverUpButton)
                .queue();
    }


}
