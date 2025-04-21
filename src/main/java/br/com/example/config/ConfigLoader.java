package br.com.example.config;

import br.com.example.enums.ConfigType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    public static Properties load() {
        return ConfigLoader.load(ConfigType.SERVER);
    }

    public static Properties load(ConfigType configuration) {
        Properties props = new Properties();
        ClassLoader classLoader = ConfigLoader.class.getClassLoader();
        String configurationFile = String.format("application-%s.properties", configuration.getType());
        try (InputStream configurations = classLoader.getResourceAsStream(configurationFile)) {
            props.load(configurations);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return props;
    }
}
