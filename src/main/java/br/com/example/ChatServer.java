package br.com.example;

import br.com.example.handler.ClientHandler;
import br.com.example.model.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer {
    private static final int PORT = 8888;
    private static final int INITIAL_CLIENTS = 10;
    private final ServerSocket server;
    private final List<Client> clientsConnected;

    public ChatServer() throws IOException {
        this(PORT, INITIAL_CLIENTS);
    }

    public ChatServer(final int port) throws IOException {
        this(port, INITIAL_CLIENTS);
    }

    public ChatServer(final int port, final int initClients) throws IOException {
        this.server = new ServerSocket(port);
        this.clientsConnected = new ArrayList<>(initClients);
    }

    public void start() throws IOException {
        System.out.println("Server started ...");
        while (!this.server.isClosed()) {
            Client client = this.acceptClient();
            new ClientHandler(client, this).process();
        }
    }

    private Client acceptClient() throws IOException {
        Socket newClient = this.server.accept();
        Client client = Client.of(newClient);
        this.clientsConnected.add(client);
        System.out.printf(">Client [%s] connected!%n", client.getId());
        return client;
    }

    public void stop() throws IOException {
        this.server.close();
    }

    public List<Client> getClientsConnected() {
        return this.clientsConnected;
    }

    public void closeClient(String id) throws IOException {
        Iterator<Client> clientIterator = this.clientsConnected.iterator();
        while (clientIterator.hasNext()) {
            Client client = clientIterator.next();
            if(client.getId() == id) {
                client.close();
                clientIterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        server.start();
    }
}