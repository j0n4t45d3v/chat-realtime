package br.com.example.strategy.command.client;

import br.com.example.enums.Command;

public class HelpCommand implements CommandStrategy {
    @Override
    public void execute(String value) {
        System.out.println();
        System.out.println("\toptions:");
        System.out.println();
        Command.helpCommands();
        System.out.println();
    }
}
