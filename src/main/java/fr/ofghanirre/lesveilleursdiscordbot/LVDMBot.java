package fr.ofghanirre.lesveilleursdiscordbot;

import fr.ofghanirre.lesveilleursdiscordbot.commands.ClearCommand;
import fr.ofghanirre.lesveilleursdiscordbot.commands.CommandManager;
import fr.ofghanirre.lesveilleursdiscordbot.commands.PingCommand;
import fr.ofghanirre.lesveilleursdiscordbot.commands.SetupCommand;
import fr.ofghanirre.lesveilleursdiscordbot.events.EventListeners;
import fr.ofghanirre.lesveilleursdiscordbot.events.SlashCommandListener;
import fr.ofghanirre.lesveilleursdiscordbot.guildcontent.GuildContentManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public final class LVDMBot {
    private final JDA bot;
    private final CommandManager commandManager;
    private final GuildContentManager guildContentManager;

    public LVDMBot(String token) {
        commandManager = new CommandManager();
        guildContentManager = new GuildContentManager();

        bot = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new EventListeners(guildContentManager), new SlashCommandListener(commandManager))
                .build();
    }

    public void start() {
        bot.getPresence().setActivity(Activity.playing("Starting..."));
        try {
            bot.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        builtinMethods();

        bot.getGuilds().forEach(guild -> {
            guild.retrieveCommands().queue(existingCommands -> {
                existingCommands.stream()
                        .filter(c -> c.getName().equals("lvdm"))
                        .forEach(c -> c.delete().queue());

                guild.upsertCommand("lvdm", "Les veilleurs de Monde's bot commands")
                        .addSubcommands(commandManager.getRegisteredCommandsData())
                        .queue();
            });
        });


        guildContentManager.load();
        bot.getPresence().setActivity(Activity.playing("🍺 Managing the taverns"));
    }

    private void builtinMethods() {
        commandManager.register(new PingCommand());
        commandManager.register(new SetupCommand(guildContentManager));
        commandManager.register(new ClearCommand(guildContentManager));
    }

    public void shutdown() {
        bot.getPresence().setActivity(Activity.playing("Shutting down..."));
        guildContentManager.save();
    }
}
