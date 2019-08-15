package com.dai.llew.kalah.responses;

public class GameCreatedResponse {

    private long id;
    private String uri;

    public GameCreatedResponse(long id) {
        this.id = id;
        // TODO hardcoded to localhost for now.
        this.uri = "http://localhost:8080/games/" + id;
    }

    public long getId() {
        return this.id;
    }

    public String getUri() {
        return this.uri;
    }
}
