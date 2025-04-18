package br.com.example.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    private final String id;
    private final Socket socket;
    private final PrintWriter output;
    private final Scanner input;

    public Client(String id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;
        this.output = new PrintWriter(socket.getOutputStream());
        this.input = new Scanner(socket.getInputStream());
    }

    public static Client of(Socket socket) throws IOException {
        String uuid = UUID.randomUUID().toString();
        return new Client(uuid, socket);
    }

    public boolean up() {
        return this.input.hasNextLine() && this.socket.isConnected();
    }

    public String receiveMessage() {
        return this.input.nextLine();
    }

    public void write(String message) {
        this.output.println(message);
        this.output.flush();
    }

    public void close() throws IOException {
        this.socket.close();
    }


    public String getId() {
        return id;
    }
}
