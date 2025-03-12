package com.java.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.apache.commons.text.similarity.CosineSimilarity;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Translate implements WorkingCommand {

    private static List<TranslationData> translationDatabase = new ArrayList<>();
    private static final Gson gson = new Gson();

    // Load the translations from the JSON file in resources when the bot starts
    static {
        try (InputStream inputStream = Translate.class.getClassLoader().getResourceAsStream("translations.json");
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            if (inputStream == null) {
                throw new IOException("translations.json file not found in resources directory");
            }

            translationDatabase = gson.fromJson(reader, new TypeToken<List<TranslationData>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static TranslationData selectedTranslation = null;  // Store selected translation globally for comparison

    public void execute(MessageReceivedEvent event) {
        if (translationDatabase.isEmpty()) {
            event.getChannel().sendMessage("No translations available. Please try again later.").queue();
            return;
        }

        // Randomly select a translation pair (Saskartan to English)
        selectedTranslation = translationDatabase.get(new Random().nextInt(translationDatabase.size()));

        // Create the embed for the translation prompt
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("**Translate this Saskartan phrase to English:**")
                .setDescription("Saskartan phrase: `" + selectedTranslation.getSaskartanText() + "`")
                .setColor(0x3498db); // A nice color for the embed

        // Send the embed with the translation request
        event.getChannel().sendMessageEmbeds(embed.build()).addActionRow(
                Button.primary("submit_translation", "Submit Translation")
        ).queue();
    }

    public static void handleButtonPress(ButtonInteractionEvent event) {
        // When the user presses the button, create and send a modal for translation input
        if (selectedTranslation == null) {
            event.reply("No phrase selected for translation!").queue();
            return;
        }

        // Create the modal for translation input
        TextInput translationInput = TextInput.create("user_translation", "Your Translation", TextInputStyle.PARAGRAPH)
                .setMinLength(1)
                .setMaxLength(1000)
                .build();

        Modal modal = Modal.create("translation_modal", "Translate the Saskartan Phrase")
                .addActionRow(translationInput)
                .build();

        // Send the modal to the user
        event.replyModal(modal).queue();
    }

    public static void handleModalSubmit(ModalInteractionEvent event) {
        System.out.println("Successfully inside Modal");

        String userTranslation = String.valueOf(event.getValue("user_translation"));  // Get the translation input from the modal
        System.out.println(userTranslation);

        // Compare the user's translation using cosine similarity
        if (selectedTranslation == null) {
            event.reply("No translation phrase selected!").queue();
            return;
        }

        // Compare using the selected translation
        double similarity = compareTranslations(userTranslation, selectedTranslation.getCorrectTranslations());

        // Respond with feedback based on similarity
        if (similarity > 0.7) {
            event.reply("Great job! Your translation is quite similar to the correct ones! Similarity: " + similarity).queue();
        } else {
            event.reply("Your translation is not very close to the correct ones. Try again! Similarity: " + similarity).queue();
        }
    }

    private static Map<CharSequence, Integer> getWordFrequencyMap(String text) {
        // Clean the text: convert to lowercase, remove only certain punctuations
        // Remove the unwanted tokens like `modalmappingtextinputvaluei`
        text = text.toLowerCase().replaceAll("[^a-z\\s]", "");  // Remove punctuation except spaces
        text = text.replaceAll("modalmappingtextinputvaluei", ""); // Remove the unwanted token

        // Split text into words using whitespace and count frequencies
        String[] words = text.split("\\s+");
        Map<CharSequence, Integer> wordFrequencyMap = new HashMap<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        return wordFrequencyMap;
    }

    public static double compareTranslations(String userTranslation, List<String> correctTranslations) {
        // Preprocess the user translation (normalize case, clean spaces)
        Map<CharSequence, Integer> userMap = getWordFrequencyMap(userTranslation);
        System.out.println("User map: " + userMap); // Debugging the user map

        // Initialize the max similarity
        double maxSimilarity = 0;

        // Iterate through each correct translation and compare with user translation
        for (int i = 0; i < correctTranslations.size(); i++) {
            String correct = correctTranslations.get(i);

            // Preprocess each correct translation
            Map<CharSequence, Integer> correctMap = getWordFrequencyMap(correct);
            System.out.println("Correct map " + (i + 1) + ": " + correctMap); // Debugging the correct map

            // Calculate the cosine similarity between the user's translation and the correct translation
            CosineSimilarity cosineSimilarity = new CosineSimilarity();
            double similarity = cosineSimilarity.cosineSimilarity(userMap, correctMap);
            System.out.println("Cosine similarity with translation " + (i + 1) + ": " + similarity); // Debugging the similarity score

            // Track the highest similarity score
            maxSimilarity = Math.max(maxSimilarity, similarity);
        }

        // After comparing with all correct translations, return the highest similarity
        return maxSimilarity;
    }

    // Create a simple class to store translation data
    static class TranslationData {
        private String saskartanText;
        private List<String> englishTranslations;

        public TranslationData(String saskartanText, List<String> englishTranslations) {
            this.saskartanText = saskartanText;
            this.englishTranslations = englishTranslations;
        }

        public String getSaskartanText() {
            return saskartanText;
        }

        public List<String> getCorrectTranslations() {
            return englishTranslations;
        }
    }
}
