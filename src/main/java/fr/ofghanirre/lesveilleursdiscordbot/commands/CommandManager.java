package fr.ofghanirre.lesveilleursdiscordbot.commands;

import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CommandManager {
    private final Map<String, ISlashCommand> commands = new HashMap<>();

    public void register(ISlashCommand command) {
        commands.put(command.getName(), command);
    }

    public Optional<ISlashCommand> get(String name) {
        return Optional.ofNullable(commands.get(name));
    }

    public Collection<SubcommandData> getRegisteredCommandsData() {
        return commands.values().stream().map(ISlashCommand::asSubCommandData).toList();
    }
}
