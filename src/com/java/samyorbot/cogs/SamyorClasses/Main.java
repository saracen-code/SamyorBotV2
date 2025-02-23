

/*
package samyorbot.cogs.SamyorClasses;
public class Main {
    public static void main(String[] args) {
        // initialize Culture, Diplomacy, Domain, Military, Province, Cities, Characters and Countries Last

        
    }
} */

package samyorbot.cogs.SamyorClasses;
import samyorbot.cogs.*;
import java.util.List;
import java.util.ArrayList;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {
        String token = System.getenv("DISCORD_TOKEN");
        // Initialize the bot with the token and required intents
        JDABuilder builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);

        // Register the FunCog (listener)
        builder.addEventListeners(new Fun());

        // Build and start the bot
        builder.build();
    }
}
