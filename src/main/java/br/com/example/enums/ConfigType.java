package br.com.example.enums;

public enum ConfigType {
    SERVER("server"), CLIENT("client");

    private String type;

    ConfigType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
