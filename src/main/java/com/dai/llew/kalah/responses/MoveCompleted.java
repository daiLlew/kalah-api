package com.dai.llew.kalah.responses;

import com.dai.llew.kalah.model.Game;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

public class MoveCompleted {

    @JsonProperty("id")
    private long gameID;

    private String url;

    @JsonProperty("status")
    private Map<Integer, Integer> pits;

    public MoveCompleted(Game game) {
        this.gameID = game.getId();
        this.url = format("http://localhost:8080/games/{0}", gameID);

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

    public String getUrl() {
        return this.url;
    }

    public Map<Integer, Integer> getPits() {
        return this.pits;
    }
}
