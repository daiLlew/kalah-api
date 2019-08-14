package com.dai.llew.kalah.game;

import java.util.ArrayList;
import java.util.List;

public class MoveDetails {

    private int gameId;
    private int chosenPitId;
    private int totalStonesMoved;
    private List<Integer> pitsIncremented;
    private boolean playerTurnOver;
    private Player player;
    private String message;
    private int stonesCaptured;

    public MoveDetails(int gameId, int chosenPitId, Player player) {
        this.gameId = gameId;
        this.chosenPitId = chosenPitId;
        this.player = player;
        this.pitsIncremented = new ArrayList<>();
    }

    public MoveDetails chosenPitId(int chosenPitId) {
        this.chosenPitId = chosenPitId;
        return this;
    }

    public MoveDetails totalStonesMoved(int totalStonesMoved) {
        this.totalStonesMoved = totalStonesMoved;
        return this;
    }

    public MoveDetails pitsIncremented(int pidId) {
        this.pitsIncremented.add(pidId);
        return this;
    }

    public MoveDetails playerTurnOver(boolean turnOver) {
        this.playerTurnOver = turnOver;
        return this;
    }

    public MoveDetails payer(Player player) {
        this.player = player;
        return this;
    }

    public MoveDetails message(String message) {
        this.message = message;
        return this;
    }

    public MoveDetails stonesCaptured(int stonesCaptured) {
        this.stonesCaptured = stonesCaptured;
        return this;
    }

    public int getGameId() {
        return this.gameId;
    }

    public int getChosenPitId() {
        return this.chosenPitId;
    }

    public int getTotalStonesMoved() {
        return this.totalStonesMoved;
    }

    public List<Integer> getPitsIncremented() {
        return this.pitsIncremented;
    }

    public boolean isPlayerTurnOver() {
        return this.playerTurnOver;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStonesCaptured() {
        return this.stonesCaptured;
    }
}
