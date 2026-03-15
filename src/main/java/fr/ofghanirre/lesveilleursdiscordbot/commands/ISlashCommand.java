package fr.ofghanirre.lesveilleursdiscordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

/**
 * Represents a slash command handled by the bot.
 *
 * <p>Each command provides its name, description and execution logic.
 * Commands implementing this interface can be registered by the
 * {@link CommandManager} and exposed as Discord subcommands.</p>
 *
 * <p>This interface is sealed to restrict implementations to the
 * predefined command classes.</p>
 */
public sealed interface ISlashCommand permits PingCommand, ClearCommand, SetupCommand {
    String getName();

    String getDescription();

    /**
     * Executes the command when triggered by a user.
     *
     * @param event the slash command interaction event
     */
    void execute(SlashCommandInteractionEvent event);

    /**
     * Converts this command into a {@link SubcommandData} instance
     * used for Discord command registration.
     *
     * @return the corresponding subcommand data
     */
    default SubcommandData asSubCommandData() {
        return new SubcommandData(getName(), getDescription());
    }
}
