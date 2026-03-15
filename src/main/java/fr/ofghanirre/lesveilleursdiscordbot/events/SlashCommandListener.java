package fr.ofghanirre.lesveilleursdiscordbot.events;

import fr.ofghanirre.lesveilleursdiscordbot.commands.CommandManager;
import fr.ofghanirre.lesveilleursdiscordbot.commands.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Listener for handling slash commands under the "lvdm" command namespace.
 *
 * <p>This class listens to all slash command interactions and delegates
 * subcommands to the corresponding {@link ISlashCommand} registered
 * in the {@link CommandManager}.</p>
 */
public class SlashCommandListener extends ListenerAdapter {
    private final CommandManager manager;

    public SlashCommandListener(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Handles incoming slash command interactions.
     *
     * <p>If the main command is "lvdm" and a valid subcommand exists,
     * the corresponding {@link ISlashCommand} is executed.</p>
     *
     * @param event the slash command interaction event
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("lvdm"))
            return;

        String sub = event.getSubcommandName();

        if (sub == null)
            return;

        manager.get(sub).ifPresent(cmd -> cmd.execute(event));
    }
}
