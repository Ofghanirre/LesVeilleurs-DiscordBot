package fr.ofghanirre.lesveilleursdiscordbot.events;

import fr.ofghanirre.lesveilleursdiscordbot.commands.CommandManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {
    private final CommandManager manager;

    public SlashCommandListener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("lvdm"))
            return;

        String sub = event.getSubcommandName();

        if (sub == null)
            return;

        manager.get(sub).ifPresent(cmd -> {
            cmd.execute(event);
        });
    }
}
