package br.com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientTest {

    private Socket connectionMock;
    private PrintWriter output;
    private Scanner input;

    @BeforeEach
    public void setUp() throws IOException {
        this.connectionMock = mock(Socket.class);
        when(this.connectionMock.getInputStream())
                .thenReturn(mock(InputStream.class));
        when(this.connectionMock.getOutputStream())
                .thenReturn(mock(OutputStream.class));

        this.output = mock(PrintWriter.class);
        this.input = mock(Scanner.class);
    }


    @Test
    @DisplayName("should create new client instance")
    void of_ShouldCreateNewClientInstance() throws IOException {
        Client newClient = Client.of(this.connectionMock);

        assertNotNull(newClient);
        assertNotNull(newClient.getId());

        verify(this.connectionMock, times(1)).getInputStream();
        verify(this.connectionMock, times(1)).getOutputStream();

    }

    @Test
    @DisplayName("should return true when client is connected")
    void up_ShouldReturnTrueWhenClientIsConnected() {
        Client newClient = new Client(
                UUID.randomUUID().toString(),
                this.connectionMock,
                this.output,
                this.input
        );
        when(this.input.hasNextLine())
                .thenReturn(true);
        when(this.connectionMock.isConnected())
                .thenReturn(true);

        boolean clientIsConnectedWithServer = newClient.up();
        assertTrue(clientIsConnectedWithServer);

        verify(this.input).hasNextLine();
        verify(this.connectionMock).isConnected();
    }

    @Test
    @DisplayName("should return false when client is not connected")
    void up_ShouldReturnTrueWhenClientIsNotConnected() {
        Client newClient = new Client(
                UUID.randomUUID().toString(),
                this.connectionMock,
                this.output,
                this.input
        );
        when(this.input.hasNextLine())
                .thenReturn(true);
        when(this.connectionMock.isConnected())
                .thenReturn(false);

        boolean clientIsConnectedWithServer = newClient.up();
        assertFalse(clientIsConnectedWithServer);

        verify(this.input).hasNextLine();
        verify(this.connectionMock).isConnected();
    }

    @Test
    @DisplayName("should return false when client is connected and input buffer is empty")
    void up_ShouldReturnTrueWhenClientIsConnectedAndInputBufferIsEmpty() {
        Client newClient = new Client(
                UUID.randomUUID().toString(),
                this.connectionMock,
                this.output,
                this.input
        );
        when(this.input.hasNextLine())
                .thenReturn(false);
        when(this.connectionMock.isConnected())
                .thenReturn(false);

        boolean clientIsConnectedWithServer = newClient.up();
        assertFalse(clientIsConnectedWithServer);

        verify(this.input, times(1)).hasNextLine();
        verify(this.connectionMock, times(0)).isConnected();
    }

    @Test
    @DisplayName("should receive input from the client and return string")
    void receiveMessage_ShouldReceiveInputFromTheClientAndReturnString() {
        Client newClient = new Client(
                UUID.randomUUID().toString(),
                this.connectionMock,
                this.output,
                this.input
        );
        String expectedReceiveMessage = "Test receive message";
        when(this.input.nextLine()).thenReturn(expectedReceiveMessage);

        String receiveMessage = newClient.receiveMessage();
        assertEquals(expectedReceiveMessage, receiveMessage);

        verify(this.input, times(1)).nextLine();
    }

    @Test
    @DisplayName("should write server response in the client output")
    void write_ShouldWriteServeResponseInTheClientOutput() {
        Client newClient = new Client(
                UUID.randomUUID().toString(),
                this.connectionMock,
                this.output,
                this.input
        );
        String message = "Test receive message";

        newClient.write(message);

        verify(this.output, times(1)).println(message);
        verify(this.output, times(1)).flush();
    }
}