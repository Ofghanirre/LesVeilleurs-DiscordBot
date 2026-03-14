package fr.ofghanirre.lesveilleursdiscordbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public final class Config {
    private static final String CONFIG_FILE_NAME = "config.properties";
    private static final String TOKEN_KEY = "token";

    public static Optional<String> extractToken() {
        try {
            var token = getToken();
            if (token.isEmpty()) {
                System.out.println(String.format("ERROR: Could not find '%s' parameter within the '%s' file.", TOKEN_KEY, CONFIG_FILE_NAME));
            }
            return token;
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not find '%s' file.", CONFIG_FILE_NAME));
        }
    }

    private static Optional<String> getToken() throws IOException {
        InputStream input = Main.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE_NAME);

        Properties props = new Properties();
        props.load(input);

        String token = props.getProperty(TOKEN_KEY);
        return Optional.ofNullable(token);
    }
}
