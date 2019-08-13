package com.dai.llew.kalah.game;

import com.dai.llew.kalah.exceptions.GameException;

import static com.dai.llew.kalah.game.GameDisplayer.displayBoard;
import static com.dai.llew.kalah.game.Player.ONE;
import static com.dai.llew.kalah.game.Player.TWO;
import static com.dai.llew.kalah.logging.LogEvent.info;

public class Game {

    private long id;
    private Pits pits;
    private Player currentPlayer;

    public Game(long id) {
        this.id = id;
        this.pits = new Pits();
        this.currentPlayer = ONE;
    }

    Game(long id, Pits pits, Player currentPlayer) {
        this.id = id;
        this.pits = pits;
        this.currentPlayer = currentPlayer;
    }

    public long getId() {
        return this.id;
    }

    public Pits getPits() {
        return this.pits;
    }

    public void makeMove(int targetPitID, Player player) {
        displayBoard(this, player);
        validatMove(targetPitID, player);
        moveStones(targetPitID, player);
       // displayBoard(this, player, targetPitID);
    }

    private int moveStones(int targetPitID, Player player) {
        Pit pit = pits.getPitByID(targetPitID);

        int pitID = targetPitID;
        int stonesAvailable = pit.takeStones();

        while (stonesAvailable > 0) {
            pitID = getNextPitID(pitID);
            Pit next = pits.getPitByID(pitID);
            if (next.addStone(player)) {
                stonesAvailable--;
            }
        }
        return pitID;
    }

    void validatMove(int pitId, Player player) {
        if (!isPlayerTurn(player))
            throw new GameException("player cannot perform move unless its their turn");

        if (!isValidPitChoice(player, pitId))
            throw new GameException("");

        if (pits.isPitEmpty(pitId))
            throw new GameException("cannot move stone from an empty pit");
    }

    boolean isPlayerTurn(Player player) {
        if (currentPlayer == null) {

        }
        return currentPlayer.equals(player);
    }

    /**
     * Check a pit id is valid for the player.
     * Player 1 can only choose pits 1 - 6
     * Player 2 can only chose pits 8 - 13
     *
     * @param player the player making the move.
     * @param pitId  the players chosen pit.
     * @return true if the choice is valid, false otherise.
     */
    boolean isValidPitChoice(Player player, int pitId) {
        if (ONE.equals(player)) {
            return pitId >= 1 && pitId <= 6;
        }
        if (TWO.equals(player)) {
            return pitId >= 8 && pitId <= 13;
        }

        info().gameID(this).log("isValidPitChoice expected player but was null");
        throw new GameException("TODO");
    }

    /**
     * Valid pit ids are 1 - 14.
     * If the current is less than 1 or greater than 14 next id return 1.
     * Otherwise return current + 1.
     */
    int getNextPitID(int current) {
        if (current >= 14 || current < 1)
            return 1;
        return current + 1;
    }
}
