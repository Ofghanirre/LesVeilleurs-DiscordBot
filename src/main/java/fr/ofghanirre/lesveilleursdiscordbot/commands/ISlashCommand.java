package fr.ofghanirre.lesveilleursdiscordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public interface ISlashCommand {
    String getName();

    String getDescription();

    void execute(SlashCommandInteractionEvent event);

    default SubcommandData asSubCommandData() {
        return new SubcommandData(getName(), getDescription());
    }
}
