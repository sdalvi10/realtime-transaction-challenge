package dev.codescreen.models;

import lombok.Getter;

@Getter
public class Ping {
    private final String serverTime;
    public Ping(String serverTime) {
        this.serverTime = serverTime;
    }
}
