package br.com.example.strategy.command.client;

public class CleanChatCommand implements CommandStrategy {

    public static final String ESCAPE_CLEAN_TERMINAL = "\u001b[H\u001b[2J";

    @Override
    public void execute(String value) {
        System.out.print(ESCAPE_CLEAN_TERMINAL);
        System.out.flush();
    }
}
