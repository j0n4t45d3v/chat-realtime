package br.com.example.handler;

import br.com.example.model.Channel;
import br.com.example.model.Client;

import java.util.Map;

public class ClientHandler implements Runnable {

    private final Client client;
    private final Map<String, Channel> channels;
    private final ExchangeHandler exchange;

    public ClientHandler(Client client, final Map<String, Channel> channels) {
        this.client = client;
        this.channels = channels;
        this.exchange = new ExchangeHandler(client, null);
    }

    public void process() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        String receiveMessage;
        while (this.client.up()) {
            receiveMessage = this.client.getMessage().trim().strip();

            if (receiveMessage.startsWith("/quit")) {
                this.quitChannel();
                break;
            }

            if (receiveMessage.startsWith("/join")) {
                this.joinChannel(receiveMessage);
                continue;
            }

            if (receiveMessage.startsWith("/create-group")) {
                this.createChannel(receiveMessage);
                continue;
            }

            this.exchange.broadcast(receiveMessage);
        }
    }

    private void quitChannel() {
        this.exchange.broadcast("Saiu do chat!");
        this.exchange.disconnect();
    }

    private void joinChannel(String receiveMessage) {
        String[] receive = receiveMessage.split(" ");
        if(receive.length < 2) {
            this.client.print("Canal não informado!");
            return;
        }
        this.exchange.switchChannel(this.channels.get(receive[1]));
    }

    private void createChannel(String receiveMessage) {
        String channelName = receiveMessage.split(" ")[1];
        if (this.channels.containsKey(channelName)) {
            this.client.print("Canal já existe use /join " + channelName + " para se conectar nele");
            return;
        }
        Channel newChannel = new Channel(channelName);
        this.channels.put(channelName, newChannel);
    }
}
