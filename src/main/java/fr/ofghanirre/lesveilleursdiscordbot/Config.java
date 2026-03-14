package fr.ofghanirre.lesveilleursdiscordbot;

import java.util.Optional;

public final class Config {
    private static final String TOKEN_KEY = "token";
    private static final String ENV_TOKEN_KEY = "DISCORD_TOKEN";

    public static Optional<String> extractToken() {
        var token = getTokenFromEnv();
        if (token.isEmpty()) {
            System.out.printf("ERROR: Could not find '%s' parameter within the env.%n", TOKEN_KEY);
        }
        return token;
    }

    private static Optional<String> getTokenFromEnv() {
        return Optional.ofNullable(System.getenv(ENV_TOKEN_KEY));
    }
}
