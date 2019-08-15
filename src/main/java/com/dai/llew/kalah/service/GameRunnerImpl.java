package com.dai.llew.kalah.service;

import com.dai.llew.kalah.exceptions.GameException;
import com.dai.llew.kalah.logging.GameDisplayer;
import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.model.GameResult;
import com.dai.llew.kalah.model.Move;
import com.dai.llew.kalah.model.Pit;
import com.dai.llew.kalah.model.Pits;
import com.dai.llew.kalah.model.Player;
import com.dai.llew.kalah.model.State;
import org.springframework.stereotype.Component;

import static com.dai.llew.kalah.logging.LogEvent.info;
import static com.dai.llew.kalah.model.Player.ONE;
import static com.dai.llew.kalah.model.Player.TWO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Responsible for managing the game play.
 */
@Component
public class GameRunnerImpl implements GameRunner {

    /**
     * Execute a player move.
     *
     * @param move the details of the move to perform.
     */
    public void executeMove(Move move) {
        GameDisplayer.displayBoard(move.getGame());
        move.validate();

        Game game = move.getGame();
        Player player = move.getPlayer();
        Pits pits = game.getPits();

        if (game.getState() == State.CREATED) {
            game.setGameState(State.IN_PROGRESS);
        }

        int lastUpdatedId = sowStones(game, player, move.getPitId());
        Pit lastUpdated = pits.getPitByID(lastUpdatedId);

        boolean isTurnEnded = evaluateTurnOutcome(lastUpdated, player, game.getPits());

        if (isTurnEnded) {
            endPlayerTurn(game);
        }
        GameDisplayer.displayBoard(move.getGame());
    }

    /**
     * Check if the game has finished. A game is finished if all pits owned by a player are empty. If true set the
     * game state to {@link State#COMPLETED}.
     *
     * @param game the game to check.
     * @return return true if the game is completed false otherwise.
     */
    public boolean isGameComplete(Game game) {
        Pits pits = game.getPits();
        int p1ActiveStones = pits.stonesRemainingInPlayerPits(ONE);
        int pActiveStones = pits.stonesRemainingInPlayerPits(TWO);

        boolean isGameEnded = p1ActiveStones == 0 || pActiveStones == 0;

        if (isGameEnded) {
            game.setGameState(State.COMPLETED);
            game.setCurrentPlayer(null);

            GameResult result = getResult(game);
            game.setResult(result);
            info().gameResult(result).gameID(game).log("game is completed");
        }
        return isGameEnded;
    }

    /**
     * Get the result of the game specified.
     *
     * @param game the game to get the results for.
     * @return {@link GameResult} for the game. Throws {@link GameException} if the game is not finished.
     */
    public GameResult getResult(Game game) {
        if (game.getState() != State.COMPLETED) {
            throw new GameException("cannot determined game result as game has not finished", BAD_REQUEST);
        }

        int p1Score = game.getPits().getPlayerFinalScore(ONE);
        int p2Score = game.getPits().getPlayerFinalScore(TWO);
        return new GameResult(p1Score, p2Score);
    }

    /**
     * Perform a move for a player. Take all stones from the specified pit and increment each following pit until the
     * available stones is 0. If a stone cannot be added to a pit i.e. it's their oppenents house then the stone is
     * carried and added to the next available pit.
     *
     * @param game   the game the move is being performed in.
     * @param player the player making the move.
     * @param pitId  the ID of the pit to take the stones from.
     * @return the pit ID of the last stone place during the move.
     */
    int sowStones(Game game, Player player, int pitId) {
        Pits pits = game.getPits();

        Pit pit = pits.getPitByID(pitId);
        int currentPitId = pitId;
        int stonesAvailable = pit.takeStones();

        while (stonesAvailable > 0) {
            currentPitId = getNextPitID(currentPitId);
            Pit next = pits.getPitByID(currentPitId);

            if (next.addStone(player)) {
                stonesAvailable--;
            }
        }
        return currentPitId;
    }

    /**
     * Given the current pit ID return the ID of the next pit. Valid pit ids are 1 - 14 if the currentPitId is less than
     * 1 or greater than 14 next id return 1. Otherwise return current + 1.
     *
     * @param currentPitId the id of the current pit.
     * @return the next valid pit ID.
     */
    int getNextPitID(int currentPitId) {
        if (currentPitId >= 14 || currentPitId < 1)
            return 1;
        return currentPitId + 1;
    }

    /**
     * Once the move has been executed check if the players turn as ended.
     *
     * @param lastUpdated the last pit updated in the move.
     * @param player      the player who made the move.
     * @param pits        the {@link Game#pits}.
     * @return true if the current player's turn has ended false otherwise.
     */
    boolean evaluateTurnOutcome(Pit lastUpdated, Player player, Pits pits) {
        if (!lastUpdated.getOwner().equals(player)) {
            System.out.println("last updated was opponents pit - player turn ends");
            return true;
        }

        if (lastUpdated.getId() == player.getHousePitId()) {
            System.out.println("last updated own house - player gets another turn");
            return false;
        }

        return handleLastUpdateToOwnPit(lastUpdated, player, pits);
    }

    /**
     * If the last stone placed is in a empty pit owned by the current player if the opposite pit is not empty take
     * the stone placed in the placed in the players pit and all stones from the opposite and add them to the players
     * house pit - known as a "capture". This ends the player turn. If the opposite pit is empty no stones are added
     * and the turn ends.
     *
     * @param lastUpdated the pit last updated when executing the move.
     * @param player      the current player.
     * @param pits        the {@link Game#pits}.
     * @return true if the current players turn ends false if they get another turn.
     */
    boolean handleLastUpdateToOwnPit(Pit lastUpdated, Player player, Pits pits) {
        if (lastUpdated.getStoneCount() > 1) {
            System.out.println("last update own non empty pit turn ends");
            return true;
        }

        Pit oppositePit = pits.getPitByID(lastUpdated.getOppositeId());

        if (oppositePit.isEmpty()) {
            System.out.println("last updated own empty pit - opposite pit empty no capture turn ends");
            return true;
        }

        captureOpponentStones(lastUpdated, oppositePit, player, pits);
        return true;
    }

    /**
     * Take all stones from the pit and the opposite pit and add them all to the players house pit.
     *
     * @param pit         the players pit.
     * @param oppositePit the pit opposite pit to capture.
     * @param player      the player executing the move.
     * @param pits        the {@link Game#pits}.
     */
    void captureOpponentStones(Pit pit, Pit oppositePit, Player player, Pits pits) {
        System.out.println("last updated own empty pit capturing stones from opponent pit - turn ends");

        int captured = pit.takeStones() + oppositePit.takeStones();
        pits.addStonesToPlayerHouse(player, captured);
    }

    /**
     * End the current players turn
     *
     * @param game the {@link Game} to update.
     */
    void endPlayerTurn(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer == ONE)
            game.setCurrentPlayer(TWO);
        else
            game.setCurrentPlayer(ONE);
    }
}
