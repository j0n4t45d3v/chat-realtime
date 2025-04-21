package br.com.example;

import br.com.example.config.ServerConfig;
import br.com.example.handler.ClientHandler;
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
    private final Map<String, Client> clientsConnected;

    public ChatServer() throws IOException {
        this(ServerConfig.INSTANCE.getPort(), ServerConfig.INSTANCE.getMaxClient());
    }

    public ChatServer(final int port, final int initClients) throws IOException {
        this.server = new ServerSocket(port);
        this.clientsConnected = new ConcurrentHashMap<>(initClients);
    }

    public void start() throws IOException {
        log.info("Server started on port: {}", this.server.getLocalPort());
        while (!this.server.isClosed()) {
            Client client = this.acceptClient();
            new ClientHandler(client, this).process();
        }
    }

    private Client acceptClient() throws IOException {
        Socket newClient = this.server.accept();
        Client client = Client.of(newClient);
        this.clientsConnected.put(client.getId(), client);
        log.info("Client {} connected!", client.getId());
        return client;
    }

    public void stop() throws IOException {
        this.server.close();
    }

    public Map<String, Client> getClientsConnected() {
        return this.clientsConnected;
    }

    public void closeClient(String id) throws IOException {
        this.clientsConnected.get(id).close();
        this.clientsConnected.remove(id);
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        server.start();
    }
}