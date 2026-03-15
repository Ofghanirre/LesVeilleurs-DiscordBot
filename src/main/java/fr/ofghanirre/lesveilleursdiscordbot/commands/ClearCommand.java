package fr.ofghanirre.lesveilleursdiscordbot.commands;

import fr.ofghanirre.lesveilleursdiscordbot.guildcontent.GuildContentManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * Administrative command used to reset the bot configuration for a guild.
 *
 * <p>This command deletes the category and voice channel created by the bot
 * and removes the stored guild configuration.</p>
 */
public final class ClearCommand implements ISlashCommand {
    private final GuildContentManager manager;

    public ClearCommand(GuildContentManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clear the server state, deleting bot-made channels and categories";
    }

    /**
     * Deletes bot-created channels and categories for the guild
     * and removes the stored configuration.
     *
     * @param event the slash command interaction event
     */
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild == null) {
            event.reply("Could not find server").queue();
            return;
        }

        manager.getContent(guild.getIdLong()).ifPresent(content -> {
            if (content.voiceChannelId() != 0) {
                VoiceChannel vc = guild.getVoiceChannelById(content.voiceChannelId());
                if (vc != null) vc.delete().queue();
            }

            if (content.categoryId() != 0) {
                Category cat = guild.getCategoryById(content.categoryId());
                if (cat != null) {
                    cat.delete().queue();
                }
            }
        });

        manager.remove(guild.getIdLong());
        manager.save();

        event.reply("Successfully cleared the %s discord server ✅".formatted(guild.getName())).queue();
    }
}
