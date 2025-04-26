package br.com.example.handler;

import br.com.example.utils.ReaderInput;

import java.io.IOException;

public class ReceiveMessage extends Thread {
    private final ReaderInput readerInput;
    private boolean running;

    public ReceiveMessage(ReaderInput readerInput) {
        this.readerInput = readerInput;
        this.running = true;
    }

    public void stopReceive() {
        this.running = false;
    }


    @Override
    public void run() {
        while (this.running) {
            try {
                if (this.readerInput.hasNexLine()) {
                    System.out.println(this.readerInput.readLine());
                }
            } catch (IOException e) {
                this.running = false;
            }
        }
    }
}
