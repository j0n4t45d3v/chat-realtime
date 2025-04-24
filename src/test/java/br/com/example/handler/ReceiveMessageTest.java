package br.com.example.handler;

import br.com.example.utils.ReaderInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class ReceiveMessageTest {

    @Test
    @DisplayName("should receive messages in other threat when client is connected")
    void shouldReceiveMessagesInOtherThreatWhenClientISConnected() throws Exception {
        ReaderInput mockInput = Mockito.mock(ReaderInput.class);

        when(mockInput.hasNexLine())
                .thenReturn(true)
                .thenReturn(false);

        when(mockInput.readLine()).thenReturn("Test message");

        ReceiveMessage thread = new ReceiveMessage(mockInput);
        thread.start();

        thread.join(500);

        verify(mockInput, atLeastOnce()).readLine();
    }
}