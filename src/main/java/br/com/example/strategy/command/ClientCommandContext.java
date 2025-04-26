package br.com.example.strategy.command;

import br.com.example.enums.Command;
import br.com.example.strategy.command.client.CleanChatCommand;
import br.com.example.strategy.command.client.CommandStrategy;
import br.com.example.strategy.command.client.HelpCommand;
import br.com.example.strategy.command.client.ServerCMDCommand;

import java.io.OutputStream;

public class ClientCommandContext {
    private final OutputStream output;
    private Command currentCommand;

    public ClientCommandContext(OutputStream output) {
        this.output = output;
        this.currentCommand = null;
    }

    public void setCommand(Command command) {
        this.currentCommand = command;
    }

    public CommandStrategy getCommand() {
        if (this.currentCommand == null) return null;
        return switch (this.currentCommand) {
            case QUIT:
            case JOIN_CHANNEL:
            case SEND_MESSAGE:
            case CREATE_CHANNEL:
                yield new ServerCMDCommand(this.output);
            case HELP_COMMAND:
                yield new HelpCommand();
            case CLEAN_CHAT:
                yield new CleanChatCommand();
        };
    }
}
