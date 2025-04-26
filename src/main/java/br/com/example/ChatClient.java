package br.com.example;

import br.com.example.enums.Command;
import br.com.example.handler.ReceiveMessage;
import br.com.example.utils.ReaderInput;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ChatClient {

    public static final String ESCAPE_CLEAN_TERMINAL = "\u001b[H\u001b[2J";

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 8888)) {
            ReaderInput clientInputReader = new ReaderInput(System.in);
            ReaderInput serverInputReader = new ReaderInput(socket.getInputStream());
            OutputStream sendToServe = socket.getOutputStream();

            ReceiveMessage receiveMessage = new ReceiveMessage(serverInputReader);
            receiveMessage.start();

            boolean isConnected = true;
            System.out.println("Escreva /help para ver os comandos que existem no chat!");
            Map<Command, Consumer<String>> options = getClientCommand(sendToServe);
            while (isConnected) {
                String text = clientInputReader.readLine();
                String command = text.split(" ")[0];
                Optional<Command> commandParsed = Command.tryParse(command);
                if (commandParsed.isEmpty()) {
                    System.out.printf("Comando %s não existe", command);
                    showHelper();
                    continue;
                }
                Consumer<String> commandExecute = options.get(commandParsed.get());
                commandExecute.accept(text);
                if (text.equals("/quit")) {
                    isConnected = false;
                    receiveMessage.stopReceive();
                }
            }
        }
    }

    private static Map<Command, Consumer<String>> getClientCommand(OutputStream sendToServe) {
        Map<Command, Consumer<String>> options = new EnumMap<>(Command.class);
        options.put(Command.CLEAN_CHAT, text -> cleanScreen());
        options.put(Command.HELP_COMMAND, text -> showHelper());
        options.put(Command.QUIT, text -> {
            text = text.trim() + '\n';
            sendMessage(sendToServe, text);
        });
        options.put(Command.SEND_MESSAGE, text -> {
            text = text.replace("/msg", "").trim() + '\n';
            System.out.println(text);
            sendMessage(sendToServe, text);
        });
        options.put(Command.JOIN_CHANNEL, options.get(Command.QUIT));
        options.put(Command.CREATE_CHANNEL, options.get(Command.QUIT));

        return options;
    }

    public static void cleanScreen() {
        System.out.print(ESCAPE_CLEAN_TERMINAL);
        System.out.flush();
    }

    public static void showHelper() {
        System.out.println();
        System.out.println("\toptions:");
        System.out.println();
        System.out.println("\t/quit                 sai do chat.");
        System.out.println("\t/clean                limpa o chat.");
        System.out.println("\t/help                 mostra essa mensagem com os comandos.");
        System.out.println("\t/join <canal>         mostra essa mensagem com os comandos.");
        System.out.println("\t/create-group         mostra essa mensagem com os comandos.");
        System.out.println("\t/msg [message]        use o prefixo '>' para poder enviar mensagens.");
        System.out.println();
    }

    public static void sendMessage(OutputStream server, String text) {
        try {
            server.write(text.getBytes(StandardCharsets.UTF_8));
            server.flush();
        } catch (IOException e) {
            System.out.println("Falha ao enviar a mensagem");
        }
    }
}
