package com.dai.llew.kalah.model;


public class GameResult {

    private int playerOneScore;
    private int playerTwoScore;
    private String winner;

    public GameResult(int playerOneScore, int playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;

        if (playerOneScore == playerTwoScore)
            this.winner = "Draw";
        else if (playerOneScore > playerTwoScore)
            this.winner = "Player one";
        else
            this.winner = "Player two";
    }

    public int getPlayerOneScore() {
        return this.playerOneScore;
    }

    public int getPlayerTwoScore() {
        return this.playerTwoScore;
    }

    public String getWinner() {
        return this.winner;
    }
}
