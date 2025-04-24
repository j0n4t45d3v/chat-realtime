package br.com.example.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    @DisplayName("should load properties in the resource path")
    void load_ShouldLoadPropertiesInTheResourcePath() {
        Properties prop = ConfigLoader.load();
        assertEquals("chat-realtime", prop.getProperty("app.name"));
    }
}