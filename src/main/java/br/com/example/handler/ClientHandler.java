package br.com.example.handler;

import br.com.example.ChatServer;
import br.com.example.model.Client;

import java.io.IOException;

public class ClientHandler implements Runnable {
    private final Client client;
    private final ChatServer server;

    public ClientHandler(Client client, ChatServer server) {
        this.client = client;
        this.server = server;
    }

    public void process() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        String receiveMessage;
        String id = this.client.getId();
        while (this.client.up()) {
            try {
                receiveMessage = this.client.receiveMessage();
                if (receiveMessage.equals("/quit")) {
                    this.server.closeClient(id);
                    receiveMessage = String.format("Saiu do chat!");
                    this.replyMessageToClientsConnected(receiveMessage);
                    break;
                }
                this.replyMessageToClientsConnected(receiveMessage);
            } catch (IOException e) {
                System.out.println("Fail in close socket connection!");
            }
        }
    }

    private void replyMessageToClientsConnected(String message) {
        message = String.format("%s > %s", this.client.getId(), message);
        System.out.println(message);
        for (Client clientConsumerMessage : this.server.getClientsConnected().values()) {
            if (!clientConsumerMessage.getId().equals(this.client.getId())) {
                clientConsumerMessage.write(message);
            }
        }
    }
}
