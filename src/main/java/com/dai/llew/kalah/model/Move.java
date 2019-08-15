package com.dai.llew.kalah.model;

import com.dai.llew.kalah.exceptions.GameException;

import static com.dai.llew.kalah.logging.LogEvent.info;
import static com.dai.llew.kalah.model.Player.ONE;
import static com.dai.llew.kalah.model.Player.TWO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class Move {

    private Game game;
    private Player player;
    private int pitId;

    public Move(Game game, Player player, int pitId) {
        this.game = game;
        this.player = player;
        this.pitId = pitId;
    }

    public Game getGame() {
        return this.game;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getPitId() {
        return this.pitId;
    }

    public void validate() {
        if (game.getState() == State.COMPLETED)
            throw new GameException("cannot execute move game is over", BAD_REQUEST);

        if (!isPlayerTurn())
            throw new GameException("player cannot perform move unless it is their turn", BAD_REQUEST);

        if (!isValidPitChoice())
            throw new GameException("player pit choice invalid", BAD_REQUEST);

        if (game.getPits().isPitEmpty(pitId))
            throw new GameException("cannot move stone from an empty pit", BAD_REQUEST);
    }

    boolean isPlayerTurn() {
        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer == null) {
            throw new GameException("internal server error", INTERNAL_SERVER_ERROR);
        }
        return currentPlayer.equals(player);
    }

    /**
     * Check a pit id is valid for the player.
     * Player 1 can only choose pits 1 - 6
     * Player 2 can only chose pits 8 - 13
     *
     * @return true if the choice is valid, false otherise.
     */
    public boolean isValidPitChoice() {
        if (ONE.equals(player)) {
            return pitId >= 1 && pitId <= 6;
        }
        if (TWO.equals(player)) {
            return pitId >= 8 && pitId <= 13;
        }

        info().gameID(game.getId()).log("isValidPitChoice expected player but was null");
        throw new GameException("internal server error", INTERNAL_SERVER_ERROR);
    }
}
