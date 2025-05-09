package br.com.example.model;

import br.com.example.manager.ClientManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class ChannelTest {

    private ClientManager clientManagerMock;

    @BeforeEach
    void setUp() {
        this.clientManagerMock = Mockito.mock(ClientManager.class);
        Client clientMockFirst = Mockito.mock(Client.class);
        Client clientMockSecond = Mockito.mock(Client.class);

        Mockito.when(this.clientManagerMock.getClients())
                .thenReturn(List.of(clientMockFirst, clientMockSecond));
    }

    @Test
    @DisplayName("should broadcast message to connected clients except the client that sent the message")
    void broadcast_ShouldBroadcastMessageToConnectedClientsExceptTheClientThatSentTheMessage() {
        Channel newChannel = new Channel("teste", this.clientManagerMock);
        Client clientMockFirst = Mockito.mock(Client.class);
        Client clientMockSecond = Mockito.mock(Client.class);

        final String idFirstClient = UUID.randomUUID().toString();
        final String idSecondClient = UUID.randomUUID().toString();
        final String broadcastMessage = "Test message";

        Mockito.when(this.clientManagerMock.getClients())
                .thenReturn(List.of(clientMockFirst, clientMockSecond));

        Mockito.when(clientMockFirst.getId())
                .thenReturn(idFirstClient);
        Mockito.when(clientMockSecond.getId())
                .thenReturn(idSecondClient);

        newChannel.broadcast(idFirstClient, broadcastMessage);

        Mockito.verify(this.clientManagerMock, Mockito.atLeastOnce())
                .getClients();
        Mockito.verify(clientMockFirst, Mockito.times(0))
                .print(broadcastMessage);
        Mockito.verify(clientMockSecond, Mockito.atLeastOnce())
                .print(String.format("[%s]> %s",idFirstClient, broadcastMessage));
    }

    @Test
    @DisplayName("should connect new client in channel")
    void connect_ShouldConnectNewClientInChannel() throws IOException {
        Channel newChannel = new Channel("teste", this.clientManagerMock);
        newChannel.connect(Mockito.mock(Client.class));
        Mockito.verify(this.clientManagerMock, Mockito.atLeastOnce())
                .connected(any(Client.class));
    }

    @Test
    @DisplayName("should disconnect client in channel")
    void disconnect() throws IOException {
        Channel newChannel = new Channel("teste", this.clientManagerMock);
        Client clientMock = Mockito.mock(Client.class);
        Mockito.when(clientMock.getId())
                .thenReturn("test-id");
        newChannel.disconnect(clientMock);
        Mockito.verify(this.clientManagerMock, Mockito.atLeastOnce())
                .disconnect(anyString());
    }

    @Test
    @DisplayName("should return channel name")
    void getName_ShouldReturnChannelName() {
        final String channelName = "teste-channel";
        Channel newChannel = new Channel(channelName);
        assertEquals(channelName, newChannel.getName());
    }
}