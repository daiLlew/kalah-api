package com.dai.llew.kalah.responses;

import com.dai.llew.kalah.game.Game;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

public class MoveCompletedResponse {

    @JsonProperty("id")
    private long gameID;

    private String uri;

    @JsonProperty("status")
    private Map<Integer, Integer> pits;

    public MoveCompletedResponse(Game game) {
        this.gameID = game.getId();
        this.uri = format("http://localhost:8080/games/{0}", gameID);

        this.pits = game.getPits()
                .getAsMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        pit -> pit.getValue().getStoneCount()));
    }

    public long getGameID() {
        return this.gameID;
    }

    public String getUri() {
        return this.uri;
    }

    public Map<Integer, Integer> getPits() {
        return this.pits;
    }
}
