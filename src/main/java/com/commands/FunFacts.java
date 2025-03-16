package com.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FunFacts {
    public static void addFunFacts(EmbedBuilder embed) {
        /// get the description
        // Path to the JSON file
        String descriptionsPath = "masterdocDescriptions.json";
        // Create Gson object
        Gson gson = new Gson();

        // Read the JSON file into a Map using InputStreamReader
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(FunFacts.class.getClassLoader().getResourceAsStream(descriptionsPath)));
        Map<String, String> descriptions = gson.fromJson(reader, new TypeToken<Map<String, String>>(){}.getType());

        // Generate a random number between 1 and 4
        Random random = new Random();
        int randomNumber = random.nextInt(4) + 1;

        // Get a random thumbnail
        int i = random.nextInt(4);
        // Initialize key and text
        String randomKey;
        String text;
        // Add a thumbnail for a medieval touch
        switch (i) {
            case 0:
                embed.setImage("https://cdn.discordapp.com/attachments/1257084823034400849/1326407194064715786/fifithfoo_a_symbaroum_art_style_painting_of_a_Birds-eye_view_of_e2adbc2f-9386-4776-993b-179622bbfd93.png?ex=67cb1eb6&is=67c9cd36&hm=ff3a176789c69c1a45122a99bcee7ee0392f09ee8d13e109dd55141dce116e81&");
                // Construct the key dynamically
                randomKey = "NyatrimDesc" + randomNumber;
                // Fetch the description using the random key
                text = descriptions.get(randomKey);
                // Add description field
                embed.addField("*Paradise at Sekale*", text, false);
                break;
            case 1:
                embed.setImage("https://media.discordapp.net/attachments/1257084823034400849/1326407566435160145/fifithfoo_a_symbaroum_art_style_Painting_of_a_large_urban_battl_e114c4ec-c6a9-4f01-84b0-616afddf1cb2.png?ex=67cb1f0f&is=67c9cd8f&hm=19cd3b851936160977c63516bf91a11e5da6af907f4c42ba8b4e0bde0e122931&=&format=webp&quality=lossless&width=799&height=799");
                // Construct the key dynamically
                randomKey = "NyMkWar" + randomNumber;
                // Fetch the description using the random key
                text = descriptions.get(randomKey);
                // Add description field
                embed.addField("*First Nyîterim-Imkîrerun War (208-211 AW)*", text, false);
                break;
            case 2:
                embed.setImage("https://media.discordapp.net/attachments/1257084823034400849/1258274763621662821/fifithfoo_a_symbaroum_art_style_painting_of_three_chinese_warlo_83069cab-c16d-4a5c-aeec-56572bc68bdf.png?ex=67cb1b71&is=67c9c9f1&hm=c1f5e47311d5543178d304642dd324a571e6441d0afda6bed45e877f6b3e69f8&=&format=webp&quality=lossless&width=831&height=831");
                // Construct the key dynamically
                randomKey = "FederCr" + randomNumber;
                // Fetch the description using the random key
                text = descriptions.get(randomKey);
                // Add description field
                embed.addField("*Formation of the Federation (c. 1593 AW)*", text, false);
                break;
            case 3:
                embed.setImage("https://media.discordapp.net/attachments/1257084823034400849/1326407487397560362/fifithfoo_a_symbaroum_art_style_Painting_of_a_huge_mongol_medie_fee17bc6-78cb-4f57-89ed-58d513127219.png?ex=67cb1efc&is=67c9cd7c&hm=ce8a014dd59cec031da1e79c9e3ed7d5114eb1e441a764c64d4228e1f3e4aba3&=&format=webp&quality=lossless&width=799&height=799");
                // Construct the key dynamically
                randomKey = "NuRaid" + randomNumber;
                // Fetch the description using the random key
                text = descriptions.get(randomKey);
                // Add description field
                embed.addField("*Great Nuryeveti Raid (447 AW)*", text, false);
                break;
            case 4:
                embed.setImage("https://media.discordapp.net/attachments/1257084823034400849/1258273206490370048/fifithfoo_a_symbaroum_art_style_painting_of_a_medieval_arab_car_82ea0d9b-f2e6-4ecc-bb76-a10ee030f412.png?ex=67cb19fe&is=67c9c87e&hm=8efc2e1fbc59dfcd433749aa33ce16e248325f29fe47c919939af020ee115a34&=&format=webp&quality=lossless&width=831&height=831");
                // Construct the key dynamically
                randomKey = "WaywDesc" + randomNumber;
                // Fetch the description using the random key
                text = descriptions.get(randomKey);
                // Add description field
                embed.addField("*The Wayward Travelers*", text, false);
                break;
        }
    }
}
