package br.com.example.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReaderInputTest {

    private InputStream inputStream;

    private ReaderInput input;

    @BeforeEach
    void setUp() {
        this.inputStream = Mockito.mock(InputStream.class);
        this.input = new ReaderInput(this.inputStream);
    }

    @Test
    @DisplayName("should read until break line")
    void readLine_ShouldReadUntilBreakLine() throws IOException {
        when(this.inputStream.read())
                .thenReturn(
                        (int) 'l',
                        (int) 'o',
                        (int) 'r',
                        (int) 'e',
                        (int) 'm',
                        (int) '\n'
                );

        String result = this.input.readLine();

        assertEquals("lorem", result);
    }

    @Test
    @DisplayName("should read word in string")
    void read_ShouldReadWordInString() throws IOException {
        when(this.inputStream.read())
                .thenReturn(
                        (int) 'i',
                        (int) 'n',
                        (int) 'p',
                        (int) 'u',
                        (int) 't',
                        (int) ' '
                );

        String result = this.input.read();

        assertEquals("input", result);
    }

    @Test
    @DisplayName("should return true when containing value in input buffer")
    void hasNextLine_ShouldReturnTrueWhenContainingValueInInputBuffer() throws IOException {
        when(this.inputStream.available()).thenReturn(0);
        boolean hasNextValue = this.input.hasNexLine();
        assertFalse(hasNextValue);
    }

    @Test
    @DisplayName("should return false when input buffer is empty")
    void hasNextLine_ShouldReturnFalseWhenInputBufferISEmpty() throws IOException {
        when(this.inputStream.available()).thenReturn(1);
        boolean hasNextValue = this.input.hasNexLine();
        assertTrue(hasNextValue);
    }
}