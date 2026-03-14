package fr.ofghanirre.lesveilleursdiscordbot.commands;

import fr.ofghanirre.lesveilleursdiscordbot.guildcontent.GuildContent;
import fr.ofghanirre.lesveilleursdiscordbot.guildcontent.GuildContentManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SetupCommand implements ISlashCommand {
    private static final String CATEGORY_NAME = "== Temporary Discussions ==";
    private static final String VOICE_CHANNEL_NAME = "🍻 Create discussion";

    private final GuildContentManager manager;

    public SetupCommand(GuildContentManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getDescription() {
        return "setup the discord server: creating the channel and categories";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild == null) return;

        GuildContent content = manager.getOrCreate(guild.getIdLong());
        Category category = guild.getCategoryById(content.categoryId());
        if (category == null) {
            guild.createCategory(CATEGORY_NAME).queue(cat -> {
                content.setCategoryId(cat.getIdLong());
                manager.save();
            });
        }

        VoiceChannel channel = guild.getChannelById(VoiceChannel.class, content.voiceChannelId());
        if (channel == null) {
            guild.createVoiceChannel(VOICE_CHANNEL_NAME).queue(vc -> {
                content.setVoiceChannelId(vc.getIdLong());
                manager.save();
            });
        }

        manager.save();
        event.reply("Successfully setup the %s discord server ✅".formatted(guild.getName())).queue();
    }
}
