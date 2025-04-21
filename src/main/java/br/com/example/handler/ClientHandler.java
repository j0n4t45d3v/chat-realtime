package br.com.example.handler;

import br.com.example.ChatServer;
import br.com.example.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ClientHandler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

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
                    receiveMessage = "Saiu do chat!";
                    this.broadcast(receiveMessage);
                    break;
                }
                this.broadcast(receiveMessage);
            } catch (IOException e) {
                log.error("Fail in close socket connection!");
            }
        }
    }

    private void broadcast(String message) {
        log.info("Client {}, send '{}'",this.client.getId(),  message);
        message = String.format("[%s]> %s", this.client.getId(), message);
        for (Client clientConsumerMessage : this.server.getClientsConnected().values()) {
            if (!clientConsumerMessage.getId().equals(this.client.getId())) {
                clientConsumerMessage.write(message);
            }
        }
    }
}
