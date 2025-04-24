package br.com.example.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerConfigTest {

    @Test
    @DisplayName("should return app.name configured in application.properties")
    void getAppName_ShouldReturn_app_name_ConfiguredInApplicationProperties() {
        String appName = ServerConfig.INSTANCE.getAppName();
        assertEquals("chat-realtime", appName);
    }

    @Test
    @DisplayName("should return app.server.port configured in application.properties")
    void getPort_ShouldReturn_app_server_port_ConfiguredInApplicationProperties() {
        int serverPort = ServerConfig.INSTANCE.getPort();
        assertEquals(8888, serverPort);
    }

    @Test
    @DisplayName("should return app.server.max-client configured in application.properties")
    void getMaxClient_ShouldReturn_app_server_max__client_ConfiguredInApplicationProperties() {
        int serverPort = ServerConfig.INSTANCE.getMaxClient();
        assertEquals(10, serverPort);
    }
}