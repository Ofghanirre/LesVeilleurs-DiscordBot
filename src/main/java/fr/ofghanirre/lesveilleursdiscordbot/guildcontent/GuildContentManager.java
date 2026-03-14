package fr.ofghanirre.lesveilleursdiscordbot.guildcontent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class GuildContentManager {
    private final File file = new File("guilds_data.json");
    private final Gson gson = new Gson();

    private final Map<Long, GuildContent> guildsContent = new HashMap<>();

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

    public void save() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(guildsContent, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GuildContent getOrCreate(long guildId) {
        return guildsContent.computeIfAbsent(guildId, GuildContent::new);
    }

    public Optional<GuildContent> getContent(long guildId) {
        return Optional.ofNullable(guildsContent.get(guildId));
    }

    public void remove(long guildId) {
        guildsContent.remove(guildId);
    }

    public Collection<GuildContent> getAll() {
        return guildsContent.values();
    }
}
