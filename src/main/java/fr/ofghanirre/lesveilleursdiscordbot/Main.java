package fr.ofghanirre.lesveilleursdiscordbot;

import java.io.IOException;

/**
 * Main entry point of the Les Veilleurs Discord Bot application.
 *
 * <p>This class is responsible for initializing the application
 * and starting the bot instance.</p>
 */
public class Main {
    private static LVDMBot bot;

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Config.extractToken().ifPresentOrElse(token -> {
            System.out.println("Starting discord BOT...");

            bot = new LVDMBot(token);
            bot.start();
            System.out.println("Successfully started!");

            try {
                WebServer.INSTANCE.start();
            } catch (IOException e) {
                System.out.println("ERROR: Failed to start WebServer!");
            }

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Bot shutting down, saving guild data...");
                Main.bot.shutdown();
                System.out.println("Stopping web server...");
                WebServer.INSTANCE.stop();
            }));


        }, () -> System.out.println("ERROR: Failed to start discord BOT!"));
    }
}