package br.com.example.strategy.command.client;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ServerCMDCommand implements CommandStrategy {

    private final OutputStream output;

    public ServerCMDCommand(OutputStream output) {
        this.output = output;
    }

    @Override
    public void execute(String value) {
        value = value.replace("/msg", "").trim() + '\n';
        try {
            output.write(value.getBytes(StandardCharsets.UTF_8));
            output.flush();
        } catch (IOException e) {
            System.out.println("Falha ao enviar o comando para o server");
        }
    }

}
