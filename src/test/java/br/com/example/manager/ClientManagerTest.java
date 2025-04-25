package br.com.example.manager;

import br.com.example.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ClientManagerTest {

    private ClientManager clientManager;
    private Client mockClient;

    @BeforeEach
    public void setUp() {
        this.clientManager = new ClientManager();
        this.mockClient = mock(Client.class);
    }

    @Test
    @DisplayName("should add new client in collection")
    void connected_ShouldAddNewClientInCollection() {
        when(this.mockClient.getId())
                .thenReturn("teste 1");
        this.clientManager.connected(this.mockClient);
        assertEquals(1, this.clientManager.getClients().size());
        verify(this.mockClient, times(0))
                .print(anyString());
    }

    @Test
    @DisplayName("should not add new client in collection when name already used in other user")
    void connected_ShouldNotAddNewClientInCollectionWhenNameAlreadyUsedInOtherUser() {
        when(this.mockClient.getId())
                .thenReturn("teste 1");
        this.clientManager.connected(this.mockClient);
        this.clientManager.connected(this.mockClient);
        assertEquals(1, this.clientManager.getClients().size());
        verify(this.mockClient, times(1))
                .print(anyString());
    }

    @Test
    @DisplayName("should remove client in collection")
    void disconnect_ShouldRemoveClientInCollection() {
        when(this.mockClient.getId())
                .thenReturn("teste 1");
        this.clientManager.connected(this.mockClient);

        when(this.mockClient.getId())
                .thenReturn("teste 2");
        this.clientManager.connected(this.mockClient);

        this.clientManager.disconnect(this.mockClient.getId());
        assertEquals(1, this.clientManager.getClients().size());
    }

    @Test
    @DisplayName("should not remove client in collection when client not found in collection")
    void disconnect_ShouldNotRemoveClientInCollectionWhenClientNotFoundInCollection() {
        when(this.mockClient.getId())
                .thenReturn("teste 1");
        this.clientManager.connected(this.mockClient);

        when(this.mockClient.getId())
                .thenReturn("teste 2");
        this.clientManager.disconnect(this.mockClient.getId());

        assertEquals(1, this.clientManager.getClients().size());
    }

    @Test
    @DisplayName("should return all clients in collection as a list")
    void getClients_ShouldReturnAllClientsInCollectionAsAList() {
        assertArrayEquals(
                List.of().toArray(),
                this.clientManager.getClients().toArray()
        );
    }
}