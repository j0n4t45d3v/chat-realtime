package br.com.example.handler;

import br.com.example.model.Channel;
import br.com.example.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExchangeHandler {

    private static final Logger log = LoggerFactory.getLogger(ExchangeHandler.class);

    private final Client client;
    private Channel currentChannel;

    public ExchangeHandler(Client client, Channel currentChannel) {
        this.client = client;
        this.currentChannel = currentChannel;
    }

    public void broadcast(String message) {
        if (this.currentChannel == null) {
            this.client.print("Você não está conectado a nenhum canal crie um ou use o /join <canal>");
            return;
        }
        this.currentChannel.broadcast(client.getId(), message);
    }

    public void switchChannel(Channel channel) {
        this.disconnect();
        this.currentChannel = channel;
        try {
            this.currentChannel.connect(this.client);
            log.info("client {} connected in channel {}!", this.client.getId(), channel.getName());
        } catch (IOException e) {
            this.client.print("Falha ao tentar se conectar ao canal " + channel);
        }
        this.client.print("Canal conectado: " + channel.getName());
    }

    public void disconnect() {
        if (this.currentChannel != null) {
            try {
                this.currentChannel.disconnect(this.client);
                log.info("client {} disconnected!", this.client.getId());
            } catch (IOException e) {
                this.client.print("Falha ao tentar se desconectar do canal " + this.currentChannel.getName());
            }
        }
    }
}
