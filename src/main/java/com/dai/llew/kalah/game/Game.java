package com.dai.llew.kalah.game;

import com.dai.llew.kalah.exceptions.GameException;

import static com.dai.llew.kalah.game.Player.ONE;
import static com.dai.llew.kalah.game.Player.TWO;
import static com.dai.llew.kalah.logging.GameDisplayer.displayBoard;
import static com.dai.llew.kalah.logging.LogEvent.error;
import static com.dai.llew.kalah.logging.LogEvent.info;
import static java.text.MessageFormat.format;

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

    Game(int id, Pits pits, Player currentPlayer) {
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

    public State getState() {
        return this.state;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void checkForWinner() {
        if (isGameCompleted()) {
            setGameState(State.COMPLETED);
            info().gameID(this).log("game ended");
            determinedWinner();
        }
    }

    public void executePlayerMove(int startingPitId, Player player) {
        if (state == State.CREATED) {
            setGameState(State.IN_PROGRESS);
        }

        MoveLog moveLog = new MoveLog(startingPitId, player);
        displayBoard(this, player);

        try {
            validatMove(startingPitId, player);

            int lastUpdatedId = sowStones(startingPitId, player, moveLog);
            Pit lastUpdated = pits.getPitByID(lastUpdatedId);

            boolean isTurnEnded = evaluateTurnOutcome(lastUpdated, player, moveLog);

            if (isTurnEnded) {
                endPlayerTurn(moveLog);
            }

            info().moveLog(moveLog).log("move successful");
            displayBoard(this, player);

        } catch (Exception ex) {
            moveLog.message(ex.getMessage());
            error().exceptionAll(ex).moveLog(moveLog).log("move unsuccessful");
        } finally {

        }
    }

    private int sowStones(int startingPitId, Player player, MoveLog event) {
        Pit pit = pits.getPitByID(startingPitId);

        int pitID = startingPitId;
        int stonesAvailable = pit.takeStones();
        event.totalStonesMoved(stonesAvailable);

        while (stonesAvailable > 0) {
            pitID = getNextPitID(pitID);
            Pit next = pits.getPitByID(pitID);

            if (next.addStone(player)) {
                event.pitsIncremented(pitID);
                stonesAvailable--;
            }
        }
        return pitID;
    }

    void validatMove(int pitId, Player player) {
        if (state == State.COMPLETED)
            throw new GameException("game is over");

        if (!isPlayerTurn(player))
            throw new GameException("player cannot perform move unless its their turn");

        if (!isValidPitChoice(player, pitId))
            throw new GameException("player pit choice invalid");

        if (pits.isPitEmpty(pitId))
            throw new GameException("cannot move stone from an empty pit");
    }

    boolean isPlayerTurn(Player player) {
        if (currentPlayer == null) {
            throw new GameException("TODO");
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

    void endPlayerTurn(MoveLog moveLog) {
        moveLog.playerTurnOver(true);

        if (currentPlayer == ONE)
            currentPlayer = TWO;
        else
            currentPlayer = ONE;
    }

    boolean evaluateTurnOutcome(Pit lastUpdated, Player player, MoveLog moveLog) {
        if (!lastUpdated.getOwner().equals(player)) {
            moveLog.message("last updated was opponents pit - player turn ends");
            return true;
        }

        if (lastUpdated.getId() == player.getHousePitId()) {
            moveLog.message("last updated own house - player gets another turn");
            return false;
        }

        return handleLastUpdateToOwnPit(lastUpdated, player, moveLog);
    }

    boolean handleLastUpdateToOwnPit(Pit lastUpdated, Player player, MoveLog moveLog) {
        if (lastUpdated.getStoneCount() > 1) {
            moveLog.message("last update own non empty pit turn ends");
            return true;
        }

        Pit oppositePit = pits.getPitByID(lastUpdated.getOppositeId());

        if (oppositePit.isEmpty()) {
            moveLog.message("last updated own empty pit - opposite pit empty no capture turn ends");
            return true;
        }

        captureStones(lastUpdated, oppositePit, player, moveLog);
        return true;
    }

    void captureStones(Pit pit, Pit oppositePit, Player player, MoveLog moveLog) {
        moveLog.message("last updated own empty pit - capturing stones from opponent pit - turn ends");
        int captured = pit.takeStones() + oppositePit.takeStones();
        moveLog.stonesCaptured(captured);
        pits.addStonesToPlayerHouse(player, captured);
    }

    /**
     * The game has ended if either player has zero stones left in any of the pits they control
     *
     * @return true if game ended false otherwise.
     */
    boolean isGameCompleted() {
        return pits.stonesRemainingInPlayerPits(ONE) == 0 || pits.stonesRemainingInPlayerPits(TWO) == 0;
    }

    void setGameState(State updated) {
        info().gameID(this).gameState(state, updated).log("game state updated");
        this.state = updated;
    }

    void determinedWinner() {
        int p1Score = pits.getPlayerFinalScore(ONE);
        int p2Score = pits.getPlayerFinalScore(TWO);

        System.out.println(format("P1: {0} P2: {1}", p1Score, p2Score));

        if (p1Score > p2Score)
            System.out.println("P1 wins");
        else if (p2Score > p1Score)
            System.out.println("P2 wins");
        else
            System.out.println("Draw");
    }
}
