package br.com.example.handler;

import br.com.example.utils.ReaderInput;

import java.io.IOException;

public class ReceiveMessage extends Thread {
    private final ReaderInput readerInput;

    public ReceiveMessage(ReaderInput readerInput) {
        this.readerInput = readerInput;
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            try {
                if (this.readerInput.hasNexLine()) {
                    System.out.println(this.readerInput.readLine());
                }
            } catch (IOException e) {
                running = false;
            }
        }
    }
}
