package fr.ofghanirre.lesveilleursdiscordbot.guildcontent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manages the storage and retrieval of {@link GuildContent} for multiple guilds.
 *
 * <p>This class handles persistence to a JSON file ("guilds_data.json") using Gson
 * and provides utility methods to access, create, and remove guild configurations.</p>
 */
public final class GuildContentManager {
    /**
     * File where guild configuration is stored.
     */
    private final File file = new File("/app/config/guilds_data.json");
    private final Gson gson = new Gson();

    /**
     * In-memory map of guild IDs to their corresponding content.
     */
    private final Map<Long, GuildContent> guildsContent = new HashMap<>();

    /**
     * Loads guild content from the JSON file into memory.
     *
     * <p>If the file does not exist, this method does nothing.</p>
     */
    public void load() {
        if (!file.exists()) return;

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<Long, GuildContent>>() {
            }.getType();
            Map<Long, GuildContent> loaded = gson.fromJson(reader, type);
            if (loaded != null) guildsContent.putAll(loaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current in-memory guild content to the JSON file.
     */
    public void save() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(guildsContent, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves existing guild content or creates a new one if it does not exist.
     *
     * @param guildId the Discord guild ID
     * @return the existing or newly created {@link GuildContent}
     */
    public GuildContent getOrCreate(long guildId) {
        return guildsContent.computeIfAbsent(guildId, GuildContent::new);
    }

    /**
     * Retrieves the guild content if it exists.
     *
     * @param guildId the Discord guild ID
     * @return an {@link Optional} containing the {@link GuildContent} if found
     */
    public Optional<GuildContent> getContent(long guildId) {
        return Optional.ofNullable(guildsContent.get(guildId));
    }

    /**
     * Removes the guild content for the specified guild.
     *
     * @param guildId the Discord guild ID
     */
    public void remove(long guildId) {
        guildsContent.remove(guildId);
    }
}
