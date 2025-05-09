package br.com.example.model;

import br.com.example.manager.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Channel {

    private static final Logger log = LoggerFactory.getLogger(Channel.class);

    private final String name;
    private final ClientManager clientManager;

    public Channel(String name) {
        this.name = name;
        this.clientManager = new ClientManager();
    }

    public Channel(String name, ClientManager clientManager) {
        this.name = name;
        this.clientManager = clientManager;
    }

    public void broadcast(String clientSentId, String message) {
        log.info("Client {}, send '{}'", clientSentId, message);
        message = String.format("[%s]> %s", clientSentId, message);
        List<Client> clientsBroadcast = this.clientManager.getClients()
                .stream()
                .filter(client -> !client.getId().equals(clientSentId))
                .toList();

        for (Client clientConsumerMessage : clientsBroadcast) {
            clientConsumerMessage.print(message);
        }
    }

    public void connect(Client client) throws IOException {
        this.clientManager.connected(client);
    }

    public void disconnect(Client client) throws IOException {
        this.clientManager.disconnect(client.getId());
    }

    public String getName() {
        return name;
    }
}
