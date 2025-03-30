package com.samyorBot.commands.embeds;

import com.samyorBot.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;
import java.util.List;

public class InvestmentInterface implements ICommand {
    public static Color interfaceColor = Color.decode("#777311");

    @Override
    public String getName() {
        return "investment";
    }

    @Override
    public String getDescription() {
        return "Manages investment opportunities, including building construction and reputation.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        sendInvestmentIntro(event.getChannel());
    }

    public static void sendInvestmentIntro(MessageChannel channel) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(interfaceColor) // Use appropriate color here
                .setTitle("**❮ Investment Menu ❯**")
                .setDescription("Welcome to the investment interface. You can allocate funds to build structures, hire workers, or let the auto-investor manage it for you.")
                .setFooter("Invest wisely, for the future of your kingdom.");

        // Buttons for different pages
        Button tutorialButton = Button.primary("investment_tutorial", "📚 Investment Tutorial");
        Button autoInvestButton = Button.secondary("auto_invest", "💰 Auto-Investor Settings");
        Button manualInvestButton = Button.success("manual_invest", "🔨 Manual Investment");

        channel.sendMessageEmbeds(embed.build())
                .setActionRow(tutorialButton, autoInvestButton, manualInvestButton)
                .queue();
    }

    // Button handler for different investment options
    public static void buttonHandler(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        if (buttonId.equals("investment_tutorial")) {
            sendInvestmentTutorial(event);
        } else if (buttonId.equals("auto_invest")) {
            sendAutoInvestorSettings(event);
        } else if (buttonId.equals("manual_invest")) {
            sendManualInvestment(event);
        }
    }

    // Send the tutorial page
    public static void sendInvestmentTutorial(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.YELLOW)
                .setTitle("Investment Tutorial")
                .setDescription("Here is how investments work:\n\n1. **Choose a building**: Select a city and choose a building to invest in.\n2. **Allocate workers**: Choose bondmen, levies, or paid workers for the task.\n3. **Completion**: Wait for the building to complete and gain benefits.")
                .setFooter("Make sure to use your funds wisely!");

        Button backButton = Button.primary("back_to_investment", "Back to Investment Menu");
        event.replyEmbeds(embed.build()).addActionRow(backButton).queue();
    }

    // Auto-investor settings page
    public static void sendAutoInvestorSettings(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.BLUE)
                .setTitle("Auto-Investor Settings")
                .setDescription("Allocate funds to the auto-investor and select a profile to invest automatically.")
                .addField("Investment Profile", "Choose a risk profile: Conservative, Balanced, or Aggressive.", false)
                .addField("Current Funds", "Funds allocated to the auto-investor: 500 Gold", false)
                .setFooter("Auto-investor will handle low-risk investments for you.");

        // Buttons for choosing risk profiles
        Button conservativeButton = Button.primary("auto_invest_conservative", "Conservative");
        Button balancedButton = Button.primary("auto_invest_balanced", "Balanced");
        Button aggressiveButton = Button.primary("auto_invest_aggressive", "Aggressive");
        Button backButton = Button.primary("back_to_investment", "Back to Investment Menu");

        event.replyEmbeds(embed.build())
                .addActionRow(conservativeButton, balancedButton, aggressiveButton, backButton)
                .queue();
    }

    // Manual investment page - Select city and buildings
    public static void sendManualInvestment(ButtonInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Manual Investment")
                .setDescription("Select the city and the building you wish to invest in. Then allocate workers to complete the task.")
                .addField("City: **Alderon**", "Available buildings: Marketplace, Barracks, Mine", false)
                .addField("Allocate Workers", "Choose between Bondmen, Levies, or Paid Workers.", false)
                .setFooter("Manual investment allows you to directly control your investments.");

        // Buttons for selecting city and workers
        Button alderonButton = Button.primary("city_alderon", "Alderon City");
        Button bondmenButton = Button.primary("workers_bondmen", "Bondmen");
        Button leviesButton = Button.primary("workers_levies", "Levies");
        Button paidWorkersButton = Button.primary("workers_paid", "Paid Workers");
        Button backButton = Button.primary("back_to_investment", "Back to Investment Menu");

        event.replyEmbeds(embed.build())
                .addActionRow(alderonButton, bondmenButton, leviesButton, paidWorkersButton, backButton)
                .queue();
    }
}
