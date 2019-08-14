package com.dai.llew.kalah.model;

import static com.dai.llew.kalah.logging.LogEvent.info;
import static com.dai.llew.kalah.model.Player.ONE;

public class Game {

    private int id;
    private State state;
    private Pits pits;
    private Player currentPlayer;

    public Game(int id) {
        this.id = id;
        this.state = State.CREATED;
        this.pits = new Pits();
        this.currentPlayer = ONE;
    }

    public Game(int id, State state, Pits pits, Player currentPlayer) {
        this.id = id;
        this.state = state;
        this.pits = pits;
        this.currentPlayer = currentPlayer;
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
}
