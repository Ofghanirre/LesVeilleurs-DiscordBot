package fr.ofghanirre.lesveilleursdiscordbot.events;

import fr.ofghanirre.lesveilleursdiscordbot.guildcontent.GuildContent;
import fr.ofghanirre.lesveilleursdiscordbot.guildcontent.GuildContentManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles Discord guild events, specifically voice channel updates.
 *
 * <p>This listener automatically creates temporary voice channels
 * when users join the designated entry channel and deletes them
 * when they become empty.</p>
 *
 * <p>It uses a {@link GuildContentManager} to store guild-specific
 * configuration and a concurrent lock map to prevent race conditions
 * when deleting channels.</p>
 */
public final class EventListeners extends ListenerAdapter {
    private final GuildContentManager manager;
    private final ConcurrentHashMap<Long, Object> channelsLock = new ConcurrentHashMap<>();

    public EventListeners(GuildContentManager manager) {
        this.manager = manager;
    }


    /**
     * Handles voice channel updates.
     *
     * <p>When a member joins the entry voice channel, a new temporary
     * voice channel is created and the member is moved there.
     * When a temporary channel becomes empty, it is automatically deleted.</p>
     *
     * @param event the guild voice update event
     */
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        final var guild = event.getGuild();
        manager.getContent(guild.getIdLong()).ifPresent(content -> {
            if (event.getChannelJoined() != null) {
                AudioChannelUnion channelJoined = event.getChannelJoined();

                if (channelJoined.getIdLong() == content.voiceChannelId()) {
                    createNewDiscussion(guild, content, event);
                }
            }

            if (event.getChannelLeft() != null) {
                AudioChannelUnion channelLeft = event.getChannelLeft();
                Category parent = channelLeft.getParentCategory();

                if (parent != null && parent.getIdLong() == content.categoryId()) {
                    Object lock = channelsLock.computeIfAbsent(channelLeft.getIdLong(), _ -> new Object());

                    synchronized (lock) {
                        if (guild.getVoiceChannelById(channelLeft.getIdLong()) == null) return;

                        long humanCount = channelLeft.getMembers().stream()
                                .filter(member -> !member.getUser().isBot())
                                .count();

                        if (humanCount == 0) {
                            deleteOldDiscussion(channelLeft);
                            channelsLock.remove(channelLeft.getIdLong());
                        }
                    }
                }
            }
        });
    }

    /**
     * Deletes an empty temporary voice channel.
     *
     * @param channel the channel to delete
     */
    private static void deleteOldDiscussion(AudioChannelUnion channel) {
        channel.delete().queue();
    }

    /**
     * Creates a new temporary voice channel and moves the member into it.
     *
     * <p>The new channel is created under the configured category and
     * grants the member permissions to manage and speak in it.</p>
     *
     * @param guild   the guild where the channel is created
     * @param content the guild configuration
     * @param event   the voice update event that triggered the creation
     */
    private void createNewDiscussion(Guild guild, GuildContent content, GuildVoiceUpdateEvent event) {
        final var member = event.getMember();
        final var name = "%s's discussion".formatted(member.getUser().getEffectiveName());

        Category newDiscussionCategory = guild.getCategoryById(content.categoryId());
        guild.createVoiceChannel(name, newDiscussionCategory)
                .setUserlimit(0)
                .queue(vc -> {
                    guild.moveVoiceMember(member, vc).queue();
                    vc.upsertPermissionOverride(member)
                            .setAllowed(Permission.MANAGE_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK)
                            .queue();
                });
    }
}
