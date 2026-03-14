package fr.ofghanirre.lesveilleursdiscordbot.guildcontent;

public final class GuildContent {
    private final long guildId;
    private long categoryId;
    private long voiceChannelId;

    public GuildContent(long guildId) {
        this.guildId = guildId;
    }

    public long guildId() {
        return guildId;
    }

    public long categoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long voiceChannelId() {
        return voiceChannelId;
    }

    public void setVoiceChannelId(long voiceChannel) {
        this.voiceChannelId = voiceChannel;
    }
}
