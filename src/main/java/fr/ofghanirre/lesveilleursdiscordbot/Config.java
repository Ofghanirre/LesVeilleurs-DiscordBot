package fr.ofghanirre.lesveilleursdiscordbot;

import java.util.Optional;

/**
 * Utility class responsible for retrieving configuration values
 * required by the bot at runtime.
 *
 * <p>Currently, this class only provides access to the Discord bot
 * token through environment variables.</p>
 *
 * <p>The token must be provided via the {@code DISCORD_TOKEN}
 * environment variable.</p>
 */
public final class Config {
    /**
     * Environment variable key used to retrieve the Discord bot token.
     */
    private static final String ENV_TOKEN_KEY = "DISCORD_TOKEN";


    private Config() {
    }

    /**
     * Extracts the Discord bot token from the environment.
     *
     * <p>If the token is not found, an error message is printed to the
     * standard output.</p>
     *
     * @return an {@link Optional} containing the bot token if present,
     * otherwise an empty {@link Optional}
     */
    public static Optional<String> extractToken() {
        var token = getTokenFromEnv();
        if (token.isEmpty()) {
            System.out.printf("ERROR: Could not find '%s' parameter within the env.%n", ENV_TOKEN_KEY);
        }
        return token;
    }

    /**
     * Retrieves the Discord bot token from the environment variables.
     *
     * @return an {@link Optional} containing the token if it exists
     */
    private static Optional<String> getTokenFromEnv() {
        return Optional.ofNullable(System.getenv(ENV_TOKEN_KEY));
    }
}
