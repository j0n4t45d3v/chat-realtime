package br.com.example.manager;

import br.com.example.model.Client;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {
    private final Map<String, Client> clientsConnected;

    public ClientManager() {
        this.clientsConnected = new ConcurrentHashMap<>();
    }

    public void connected(Client client) {
        if (this.alreadyExistClientConnectedWithName(client.getId())) {
            client.print("Já existe um usuário conectado com esse nome!");
            return;
        }
        this.clientsConnected.put(client.getId(), client);
    }

    public void disconnect(String id) {
        if (this.alreadyExistClientConnectedWithName(id)) {
            this.clientsConnected.remove(id);
        }
    }

    public boolean alreadyExistClientConnectedWithName(String id) {
        Optional<Client> client = Optional.ofNullable(this.clientsConnected.get(id));
        return client.isPresent();
    }

    public List<Client> getClients() {
        return this.clientsConnected
                .values()
                .stream()
                .toList();
    }
}
