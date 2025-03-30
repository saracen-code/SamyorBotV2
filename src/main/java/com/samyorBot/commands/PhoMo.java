package com.samyorBot.commands;

import com.samyorBot.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.ArrayList;

public class PhoMo implements ICommand {

    @Override
    public String getName() {
        return "phomo";
    }

    @Override
    public String getDescription() {
        return "Saskartanizes a word";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "word", "The word to Saskartanize", true));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String message = event.getOption("word").getAsString();
        String input = message.toLowerCase();  // Get input in lowercase

        // Step 1: Reverse map the input
        String reversedInput = reverseMapping(input);

        // Step 2: Saskartanize the input again using original mapping
        String saskartanizedInput = applyPhonology(reversedInput);

        event.reply("Your word in Saskartanized format becomes " + saskartanizedInput).queue();
    }

    // Reverse mapping function
    private String reverseMapping(String input) {
        String[][] reverseMap = {
                {"kh", "x"},
                {"sh", "c"},
                {"gh", "v"},
                {"nn", "1"},
                {"gn", "2"},
                {"'h", "3"},
                {"th", "4"},
                {"dh", "5"},
                {"' ", "6"},
                {"-", "7"},
                {"ph", "p"},
                {"iy", "i"},
                {"â", "o"},
                {"u", "u"},
                {"a", "a"},
                {"e", "e"},
                {"i", "i"},
                {"o", "o"},
                {"u", "u"},
                {"q", "q"},
                {"w", "w"},
                {"r", "r"},
                {"t", "t"},
                {"y", "y"},
                {"s", "s"},
                {"d", "d"},
                {"f", "f"},
                {"g", "g"},
                {"h", "h"},
                {"j", "j"},
                {"k", "k"},
                {"l", "l"},
                {"z", "z"},
                {"b", "b"},
                {"n", "n"},
                {"m", "m"},
        };

        // Apply reverse mapping
        for (String[] mapping : reverseMap) {
            input = input.replace(mapping[0], mapping[1]);
        }
        return input;
    }

    // Original Saskartanization mapping function
    private String applyPhonology(String input) {
        String[][] saskartanizeMap = {
                {"q", "q"},
                {"w", "w"},
                {"r", "r"},
                {"t", "t"},
                {"y", "y"},
                {"s", "s"},
                {"d", "d"},
                {"f", "f"},
                {"g", "g"},
                {"h", "h"},
                {"j", "j"},
                {"k", "k"},
                {"l", "l"},
                {"z", "z"},
                {"b", "b"},
                {"n", "n"},
                {"m", "m"},
                {"x", "kh"},
                {"c", "sh"},
                {"v", "gh"},
                {"1", "nn"},
                {"2", "gn"},
                {"3", "'h"},
                {"4", "th"},
                {"5", "dh"},
                {"6", "' "},
                {"7", "-"},
                {"p", "ph"},
                {"i", "iy"},
                {"o", "â"},
                {"u", "u"},
                {"a", "a"},
                {"e", "e"},
                {"i", "i"},
                {"o", "o"},
                {"u", "u"},
        };

        // Apply original Saskartanization mapping
        for (String[] mapping : saskartanizeMap) {
            input = input.replace(mapping[0], mapping[1]);
        }
        return input;
    }
}
