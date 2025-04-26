package br.com.example.enums;

import jdk.dynalink.linker.LinkerServices;

import java.io.LineNumberInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Command {
    QUIT("/quit"),
    JOIN_CHANNEL("/join"),
    HELP_COMMAND("/help"),
    CREATE_CHANNEL("/create-group"),
    SEND_MESSAGE("/msg"),
    CLEAN_CHAT("/clean");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<Command> tryParse(String value) {
        Map<String, Command> commands = Arrays.stream(Command.values())
                .collect(Collectors.toMap(Command::getValue, Function.identity()));
            return Optional.ofNullable(commands.get(value));
    }
}
