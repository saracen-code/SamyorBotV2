package com.commands.setup;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public abstract class CommandBase {
    public abstract void execute(SlashCommandInteractionEvent event);
    public abstract void execute(MessageReceivedEvent event);

    protected Btn createButton(String id, String label) {
        return new Btn(id, label, null);
    }

    protected Btn createButton(String id, Emoji emoji) {
        return new Btn(id, null, emoji);
    }

    protected static class Btn {
        private final String id;
        private final String label;
        private final Emoji emoji;

        public Btn(String id, String label, Emoji emoji) {
            this.id = id;
            this.label = label;
            this.emoji = emoji;
        }

        public Button primary() {
            return label != null ? Button.primary(id, label) : Button.primary(id, emoji);
        }

        public Button secondary() {
            return label != null ? Button.secondary(id, label) : Button.secondary(id, emoji);
        }

        public Button success() {
            return label != null ? Button.success(id, label) : Button.success(id, emoji);
        }

        public Button danger() {
            return label != null ? Button.danger(id, label) : Button.danger(id, emoji);
        }
    }
}
