package fr.ofghanirre.lesveilleursdiscordbot.commands;

import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manages the registration and retrieval of slash commands.
 *
 * <p>This class acts as a central registry for all bot commands
 * and provides utilities to expose them to the Discord API.</p>
 */
public final class CommandManager {
    /**
     * Registered commands indexed by their name.
     */
    private final Map<String, ISlashCommand> commands = new HashMap<>();

    /**
     * Registers a new slash command.
     *
     * @param command the command to register
     */
    public void register(ISlashCommand command) {
        commands.put(command.getName(), command);
    }

    /**
     * Retrieves a command by its name.
     *
     * @param name the command name
     * @return an {@link Optional} containing the command if found
     */
    public Optional<ISlashCommand> get(String name) {
        return Optional.ofNullable(commands.get(name));
    }

    /**
     * Returns the list of registered commands formatted for
     * Discord slash command registration.
     *
     * @return a collection of {@link SubcommandData}
     */
    public Collection<SubcommandData> getRegisteredCommandsData() {
        return commands.values().stream().map(ISlashCommand::asSubCommandData).toList();
    }
}
