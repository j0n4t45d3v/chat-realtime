package br.com.example;

import br.com.example.handler.ReceiveMessage;
import br.com.example.utils.ReaderInput;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClient {

    public static final String ESCAPE_CLEAN_TERMINAL = "\\u001b[H\\u001b[2J";

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 8888)) {
            ReaderInput clientInputReader = new ReaderInput(System.in);
            ReaderInput serverInputReader = new ReaderInput(socket.getInputStream());
            OutputStream sendToServe = socket.getOutputStream();

            ReceiveMessage receiveMessage = new ReceiveMessage(serverInputReader);
            receiveMessage.start();

            boolean isConnected = true;
            while (isConnected) {
                String text = clientInputReader.readLine();
                System.out.println("> "+ text);
                if (text.equals("/clean")) {
                    System.out.println(ESCAPE_CLEAN_TERMINAL);
                }
                sendToServe.write(text.getBytes(StandardCharsets.UTF_8));
                sendToServe.flush();
            }
        }
    }
}
