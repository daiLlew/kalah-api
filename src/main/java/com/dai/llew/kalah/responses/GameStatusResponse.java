package com.dai.llew.kalah.responses;

import com.dai.llew.kalah.game.Game;

public class GameStatusResponse {

    private long gameId;
    private String state;
    private String playerTurn;
    private String lastMoveLog;

    public GameStatusResponse(Game game) {
        this.gameId = game.getId();
        this.state = game.getState().name();
        this.playerTurn = game.getCurrentPlayer().getId();
        this.lastMoveLog = game.getLastMoveLog();
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

    public String getLastMoveLog() {
        return this.lastMoveLog;
    }
}
