package com.dai.llew.kalah.responses;

import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.model.GameResult;
import com.fasterxml.jackson.annotation.JsonInclude;

public class GameStatusResponse {

    private long gameId;
    private String state;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String playerTurn;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GameResult result;

    public GameStatusResponse(Game game) {
        this.gameId = game.getId();
        this.state = game.getState().name();
        this.result = game.getResult();

        if (game.getCurrentPlayer() != null) {
            this.playerTurn = game.getCurrentPlayer().getId();
        }
    }

    public long getGameId() {
        return this.gameId;
    }

    public String getState() {
        return this.state;
    }

    public String getPlayerTurn() {
        return this.playerTurn;
    }

    public GameResult getResult() {
        return this.result;
    }
}
