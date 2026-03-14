package fr.ofghanirre.lesveilleursdiscordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand implements ISlashCommand {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "ping the bot to know if it is (a)live";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("The discord bot is active (pong)").queue();
    }
}
