package com.dai.llew.kalah.model;

import static com.dai.llew.kalah.logging.LogEvent.info;
import static com.dai.llew.kalah.model.Player.ONE;

public class Game {

    private int id;
    private State state;
    private Pits pits;
    private Player currentPlayer;
    private GameResult result;

    public Game(int id) {
        this.id = id;
        this.state = State.CREATED;
        this.pits = new Pits();
        this.currentPlayer = ONE;
        this.result = null;
    }

    public Game(int id, State state, Pits pits, Player currentPlayer, GameResult result) {
        this.id = id;
        this.state = state;
        this.pits = pits;
        this.currentPlayer = currentPlayer;
        this.result = result;
    }

    public int getId() {
        return this.id;
    }

    public Pits getPits() {
        return this.pits;
    }

    public State getState() {
        return this.state;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setGameState(State updated) {
        info().gameID(this).gameState(state, updated).log("model state updated");
        this.state = updated;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameResult getResult() {
        return this.result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }
}
