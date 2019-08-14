package com.dai.llew.kalah.responses;

import com.dai.llew.kalah.model.Game;

public class GameStatusResponse {

    private long gameId;
    private String state;
    private String playerTurn;

    public GameStatusResponse(Game game) {
        this.gameId = game.getId();
        this.state = game.getState().name();
        this.playerTurn = game.getCurrentPlayer().getId();
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
}
