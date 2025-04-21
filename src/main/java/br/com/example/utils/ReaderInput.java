package br.com.example.utils;

import java.io.IOException;
import java.io.InputStream;

public class ReaderInput {

    private final InputStream input;

    enum ASCII {
        SPACE(32), LF(10), RF(13);

        private final int asciiCode;

        ASCII(int asciiCode) {
            this.asciiCode = asciiCode;
        }

        public int getAsciiCode() {
            return asciiCode;
        }

        public static boolean isTerminateLine(int asciiCode) {
            return asciiCode == ASCII.LF.getAsciiCode() || asciiCode == ASCII.RF.getAsciiCode();
        }

        public static boolean isSpace(int asciiCode) {
            return asciiCode == ASCII.SPACE.getAsciiCode();
        }
    }

    public ReaderInput(InputStream input) {
        this.input = input;
    }

    public String readLine() throws IOException {
        StringBuilder text = new StringBuilder();
        int character = this.input.read();
        while (!ASCII.isTerminateLine(character)) {
            text.append((char) character);
            character = this.input.read();
        }
        text.append('\n');
        return text.toString();
    }

    public String read() throws IOException {
        StringBuilder text = new StringBuilder();
        int character = this.input.read();
        while (!ASCII.isSpace(character)) {
            text.append((char) character);
            character = this.input.read();
        }
        text.append('\n');
        return text.toString();
    }

    public boolean hasNexLine() throws IOException {
        return this.input.available() > 0;
    }
}
