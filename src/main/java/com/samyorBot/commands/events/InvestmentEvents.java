package com.samyorBot.commands.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;

public class InvestmentEvents {
    public static void royalPalaceConstructionDelayed(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.yellow)
                .setTitle("🏰 Royal Palace Construction Delayed!")
                .setDescription("Construction on the royal palace has been delayed due to unforeseen circumstances. You need to decide how to address this issue.");

        String consequences = "1. **Invest extra resources** to expedite the construction. (Cost: 300 Gold, +10 Reputation, accelerates project)\n" +
                "2. **Negotiate with contractors** to find a more affordable solution. (Cost: 150 Gold, delays project by 2 turns, +5 Reputation)\n" +
                "3. **Cut corners** to save money. (Risk: -10 Reputation, could cause future structural problems, delays by 5 turns)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("The future of your capital could depend on how you handle this delay!");

        // Buttons for choices
        Button investExtraButton = Button.primary("invest_extra", "Invest Extra Resources");
        Button negotiateButton = Button.primary("negotiate_contractors", "Negotiate with Contractors");
        Button cutCornersButton = Button.danger("cut_corners", "Cut Corners");

        event.replyEmbeds(embed.build())
                .addActionRow(investExtraButton, negotiateButton, cutCornersButton)
                .queue();
    }

    public static void fireInNewDistrict(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("🔥 A Fire Breaks Out in the New District!")
                .setDescription("A massive fire is currently raging through the newly developed district. The damage could set back your investments unless you act quickly.");

        String consequences = "1. **Build a fire brigade** to control the situation. (Cost: 200 Gold, +5 Reputation, mitigates damage)\n" +
                "2. **Mobilize the citizens** to help fight the fire. (Cost: 100 Gold, no impact on reputation, reduces damage)\n" +
                "3. **Ignore the fire for now** and focus on other projects. (Risk: -15 Reputation, damage to buildings, future costs to repair)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("A swift decision could save your investments!");

        // Buttons for choices
        Button buildFireBrigadeButton = Button.primary("build_fire_brigade", "Build Fire Brigade");
        Button mobilizeCitizensButton = Button.primary("mobilize_citizens", "Mobilize the Citizens");
        Button ignoreFireButton = Button.danger("ignore_fire", "Ignore the Fire");

        event.replyEmbeds(embed.build())
                .addActionRow(buildFireBrigadeButton, mobilizeCitizensButton, ignoreFireButton)
                .queue();
    }

    public static void corruptionInConstruction(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("🤐 Corruption in the Construction Department!")
                .setDescription("There are rumors of corruption within the construction department. If you don't take action, your projects may face delays or increased costs due to bribery and inefficiency.");

        String consequences = "1. **Investigate and remove corrupt officials** to restore order. (Cost: 250 Gold, +10 Reputation, future projects more efficient)\n" +
                "2. **Ignore the rumors** and continue without interference. (Risk: -10 Reputation, future projects may be delayed or cost more)\n" +
                "3. **Cover up the corruption** and hope it doesn't affect the projects. (Risk: -20 Reputation, severe long-term consequences, project delays)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("Your response will determine the future quality of construction!");

        // Buttons for choices
        Button investigateCorruptionButton = Button.primary("investigate_corruption", "Investigate and Remove Officials");
        Button ignoreRumorsButton = Button.danger("ignore_rumors", "Ignore the Rumors");
        Button coverUpButton = Button.danger("cover_up_corruption", "Cover Up the Corruption");

        event.replyEmbeds(embed.build())
                .addActionRow(investigateCorruptionButton, ignoreRumorsButton, coverUpButton)
                .queue();
    }

    public static void newConstructionRegulations(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.BLUE)
                .setTitle("🏗️ New Construction Regulations!")
                .setDescription("New regulations have been passed by the mayor, requiring investors to comply with stricter safety and quality standards in their construction projects. This will affect both costs and timelines.");

        String consequences = "1. **Comply with the new regulations** and ensure all projects meet the new standards. (Cost: +20% to project cost, +10 Reputation, projects delayed by 1 turn)\n" +
                "2. **Push back against the regulations** and try to find loopholes. (Risk: -5 Reputation, future inspections may be costly)\n" +
                "3. **Ignore the regulations** for now. (Risk: -20 Reputation, fines for non-compliance, future projects delayed and more costly)";

        embed.addField("What will you do?", consequences, false)
                .setFooter("Your choice will shape the future of your city’s construction process!");

        // Buttons for choices
        Button complyButton = Button.primary("comply_regulations", "Comply with Regulations");
        Button pushBackButton = Button.danger("push_back_regulations", "Push Back Against Regulations");
        Button ignoreButton = Button.danger("ignore_regulations", "Ignore Regulations");

        event.replyEmbeds(embed.build())
                .addActionRow(complyButton, pushBackButton, ignoreButton)
                .queue();
    }
}
