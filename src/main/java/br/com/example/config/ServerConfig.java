package br.com.example.config;

import java.util.Properties;

public enum ServerConfig {
    INSTANCE;
    private String appName;
    private int port;
    private int maxClient;

    ServerConfig() {
        Properties configurations = ConfigLoader.load();
        this.appName = configurations.getProperty("app.name", "chat-realtime");
        this.port = Integer.parseInt(configurations.getProperty("app.server.port", "8888"));
        this.maxClient= Integer.parseInt(configurations.getProperty("app.server.max-client", "10"));
    }

    public String getAppName() {
        return this.appName;
    }

    public int getPort() {
        return this.port;
    }

    public int getMaxClient() {
        return this.maxClient;
    }
}
