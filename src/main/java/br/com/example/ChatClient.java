package br.com.example;

import br.com.example.enums.Command;
import br.com.example.handler.ReceiveMessage;
import br.com.example.strategy.command.ClientCommandContext;
import br.com.example.utils.ReaderInput;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

public class ChatClient {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 8888)) {
            ReaderInput clientInputReader = new ReaderInput(System.in);
            ReaderInput serverInputReader = new ReaderInput(socket.getInputStream());
            OutputStream sendToServe = socket.getOutputStream();

            ReceiveMessage receiveMessage = new ReceiveMessage(serverInputReader);
            receiveMessage.start();

            boolean isConnected = true;
            System.out.println("Escreva /help para ver os comandos que existem no chat!");
            ClientCommandContext context = new ClientCommandContext(sendToServe);
            while (isConnected) {
                String text = clientInputReader.readLine();
                String command = text.split(" ")[0];
                Optional<Command> commandParsed = Command.tryParse(command);
                if (commandParsed.isEmpty()) {
                    System.out.printf("Comando %s não existe", command);
                    context.setCommand(Command.HELP_COMMAND);
                    context.getCommand().execute(text);
                    continue;
                }
                context.setCommand(commandParsed.get());
                context.getCommand().execute(text);
                if (text.equals("/quit")) {
                    isConnected = false;
                    receiveMessage.stopReceive();
                }
            }
        }
    }
}
