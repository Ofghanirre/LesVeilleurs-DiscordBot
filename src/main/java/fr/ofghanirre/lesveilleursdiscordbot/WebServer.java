package fr.ofghanirre.lesveilleursdiscordbot;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * This is used mainly for google cloud run service.
 * It allows for a socket listener allowing the app to run 24/7.
 */
public final class WebServer {
    public static final WebServer INSTANCE = new WebServer();
    private static HttpServer server;

    private WebServer() {
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", exchange -> {
            String response = "Bot is alive!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        server.start();
    }

    public void stop() {
        server.stop(0);
    }
}
