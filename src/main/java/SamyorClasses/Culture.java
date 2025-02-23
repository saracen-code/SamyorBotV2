package SamyorClasses;
import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Random;

public class Culture {
    public static String STANDARD_CULTURE = "Kaiyuanian";
    private String name;
    private String language;
    private String kingName;
    private String cultureUnit;

    public Culture(String name, String language) {
        this.name = name;
        this.language = language;
    }

    // GET
    public String getName() {
        return this.name;
    }
    public String getLanguage() {
        return this.language;
    }
    public String getKingName() {
        if (kingName != null) {
            return this.kingName;
        } else {
            return "Egden";
        }
    }
    public String getCultureUnit() {
        return cultureUnit;
    }
    public static String getCulturalName(Culture culture) {
        try {
            // Read the JSON file
            String content = new String(Files.readAllBytes(Paths.get("names.json")));
            JSONObject json = new JSONObject(content);

            // Get the names list for the specified culture
            JSONObject cultures = json.getJSONObject("cultures");
            if (cultures.has(culture.getName())) {
                JSONArray namesArray = cultures.getJSONArray(culture.getName());
                // Generate a random index to pick a name
                Random rand = new Random();
                int randomIndex = rand.nextInt(namesArray.length());

                // Get the random name and print it
                String randomName = namesArray.getString(randomIndex);
                System.out.println("Random " + culture.getName() + " Name: " + randomName);
                return randomName;
            } else {
                JSONArray namesArray = cultures.getJSONArray(STANDARD_CULTURE);
                System.out.println("Culture not found: " + culture.getName() + "...Picking standard name.");
                // Generate a random index to pick a name
                Random rand = new Random();
                int randomIndex = rand.nextInt(namesArray.length());

                // Get the random name and print it
                String randomName = namesArray.getString(randomIndex);
                System.out.println("Random " + culture.getName() + " Name: " + randomName);
                return randomName;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
