package br.com.example.handler;

import br.com.example.utils.ReaderInput;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReceiveMessageTest {

    private static final PrintStream mainOut = System.out;
    private static final ByteArrayOutputStream bytesOutStream = new ByteArrayOutputStream();

    @BeforeAll
    static void setUp() {
        System.setOut(new PrintStream(bytesOutStream));
    }

    @AfterAll
    static void tearDown() {
        System.setOut(mainOut);
    }

    @Test
    @DisplayName("should receive messages in other threat when client is connected")
    void shouldReceiveMessagesInOtherThreatWhenClientISConnected() throws Exception {
        final String mockMessage = "Test message";
        ReaderInput mockInput = Mockito.mock(ReaderInput.class);

        when(mockInput.hasNexLine())
                .thenReturn(true)
                .thenReturn(false);

        when(mockInput.readLine()).thenReturn(mockMessage);

        ReceiveMessage thread = new ReceiveMessage(mockInput);
        thread.start();

        thread.join(500);

        assertEquals(
                mockMessage + System.lineSeparator(),
                bytesOutStream.toString()
        );

        verify(mockInput, atLeastOnce()).readLine();
    }
}