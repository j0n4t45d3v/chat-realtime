package br.com.example.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Command {
    QUIT("/quit", "sai do chat"),
    JOIN_CHANNEL("/join", "se junta a um canal que ja existe", "canal"),
    HELP_COMMAND("/help", "mostra essa mensagem com os comandos"),
    CREATE_CHANNEL("/create-group", "cria um novo canal de conversa", "novo-canal"),
    SEND_MESSAGE("/msg", "envia uma mensagem para o canal atual", "mensagem"),
    CLEAN_CHAT("/clean", "limpa o chat");

    private final String value;
    private final String helpDescription;
    private final String valueForCommand;

    Command(String value, String helpDescription) {
        this.value = value;
        this.helpDescription = helpDescription;
        this.valueForCommand = null;
    }

    Command(String value, String helpDescription, String valueForCommand) {
        this.value = value;
        this.helpDescription = helpDescription;
        this.valueForCommand = valueForCommand;
    }

    public String getValue() {
        return value;
    }

    public static Optional<Command> tryParse(String value) {
        Map<String, Command> commands = Arrays.stream(Command.values())
                .collect(Collectors.toMap(Command::getValue, Function.identity()));
        return Optional.ofNullable(commands.get(value));
    }

    public static void helpCommands() {
        Arrays.asList(Command.values())
                .forEach(cmd -> {
                    if (cmd.valueForCommand != null) {
                        System.out.printf("\t%s [%s] - %s%n", cmd.value, cmd.valueForCommand, cmd.helpDescription);
                        return;
                    }
                    System.out.printf("\t%s %s%n", cmd.value, cmd.helpDescription);
                });
    }
}
