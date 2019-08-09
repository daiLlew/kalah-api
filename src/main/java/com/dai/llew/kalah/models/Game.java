package com.dai.llew.kalah.models;

public class Game {

    private long id;
    private String uri;

    public Game(long id) {
        this.id = id;
        this.uri = "http://localhost:8080/games/" + id;
    }

    public long getId() {
        return this.id;
    }

    public String getUri() {
        return this.uri;
    }
}
