package com.dai.llew.kalah.model;

import static com.dai.llew.kalah.model.Player.ONE;
import static com.dai.llew.kalah.model.Player.TWO;
import static java.text.MessageFormat.format;

public class GameResult {

    private int gameId;
    private int playerOneScore;
    private int playerTwoScore;
    private String message;

    public GameResult(Game game) {
        this.gameId = game.getId();
        this.playerOneScore = game.getPits().getPlayerFinalScore(ONE);
        this.playerTwoScore = game.getPits().getPlayerFinalScore(TWO);
    }

    public int getGameId() {
        return this.gameId;
    }

    public int getPlayerOneScore() {
        return this.playerOneScore;
    }

    public int getPlayerTwoScore() {
        return this.playerTwoScore;
    }

    public String getMessage() {
        if (playerOneScore == playerTwoScore) {
            return format("Draw! Scores: P1={0} P2={1}\n", playerOneScore, playerTwoScore);
        }

        Player player = Player.ONE;
        if (playerTwoScore > playerOneScore) {
            player = Player.TWO;
        }
        return format("Player {0} wins! P1: {1} P2: {2}\n", player.getId(), playerOneScore, playerTwoScore);
    }
}
