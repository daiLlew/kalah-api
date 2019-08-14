package com.dai.llew.kalah.game;

import java.util.ArrayList;
import java.util.List;

public class MoveLog {

    private int chosenPitId;
    private int totalStonesMoved;
    private List<Integer> pitsIncremented;
    private boolean playerTurnOver;
    private Player player;
    private String message;
    private int stonesCaptured;

    public MoveLog(int chosenPitId, Player player) {
        this.chosenPitId = chosenPitId;
        this.player = player;
        this.pitsIncremented = new ArrayList<>();
    }

    public MoveLog chosenPitId(int chosenPitId) {
        this.chosenPitId = chosenPitId;
        return this;
    }

    public MoveLog totalStonesMoved(int totalStonesMoved) {
        this.totalStonesMoved = totalStonesMoved;
        return this;
    }

    public MoveLog pitsIncremented(int pidId) {
        this.pitsIncremented.add(pidId);
        return this;
    }

    public MoveLog playerTurnOver(boolean turnOver) {
        this.playerTurnOver = turnOver;
        return this;
    }

    public MoveLog payer(Player player) {
        this.player = player;
        return this;
    }

    public MoveLog message(String message) {
        this.message = message;
        return this;
    }

    public MoveLog stonesCaptured(int stonesCaptured) {
        this.stonesCaptured = stonesCaptured;
        return this;
    }
}
