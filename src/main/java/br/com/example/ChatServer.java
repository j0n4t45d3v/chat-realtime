package br.com.example;

import br.com.example.config.ServerConfig;
import br.com.example.handler.ClientHandler;
import br.com.example.model.Channel;
import br.com.example.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private static final Logger log = LoggerFactory.getLogger(ChatServer.class);

    private final ServerSocket server;
    private final Map<String, Channel> channels;

    public ChatServer() throws IOException {
        this(ServerConfig.INSTANCE.getPort(), ServerConfig.INSTANCE.getMaxClient());
    }

    public ChatServer(final int port, final int initClients) throws IOException {
        this.server = new ServerSocket(port);
        this.channels = new ConcurrentHashMap<>(initClients);
    }

    public void start() throws IOException {
        log.info("Server started on port: {}", this.server.getLocalPort());
        while (!this.server.isClosed()) {
            Client client = this.acceptClient();
            new ClientHandler(client, channels).process();
        }
    }

    private Client acceptClient() throws IOException {
        Socket newClient = this.server.accept();
        Client client = Client.of(newClient);
        log.info("Client {} connected!", client.getId());
        return client;
    }

    public void stop() throws IOException {
        this.server.close();
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = null;
        try {
            server = new ChatServer();
            server.start();
        } finally {
            if (server != null) {
                server.stop();
            }
        }
    }
}