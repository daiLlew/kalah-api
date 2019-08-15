package com.dai.llew.kalah.controllers;

import com.dai.llew.kalah.exceptions.GameException;
import com.dai.llew.kalah.logging.GameDisplayer;
import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.model.Move;
import com.dai.llew.kalah.model.Player;
import com.dai.llew.kalah.responses.GameCreated;
import com.dai.llew.kalah.responses.GameStatus;
import com.dai.llew.kalah.responses.MoveCompleted;
import com.dai.llew.kalah.service.GameRunner;
import com.dai.llew.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class GamesController {

    static final String INTERNAL_SERVER_ERR_MSG = "internal server error";

    @Autowired
    private GameRunner gameRunner;

    @Autowired
    private GameService gameService;

    /**
     * Create a new game
     */
    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public GameCreated newGame() {
        try {
            long gameID = gameService.createNewGame();
            return new GameCreated(gameID);
        } catch (GameException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        } catch (Exception ex) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERR_MSG);
        }
    }

    /**
     * Get all games.
     */
    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGames() {
        try {
            return gameService.getGames();
        } catch (GameException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        } catch (Exception ex) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERR_MSG);
        }
    }

    /**
     * Get the status of the specified game.
     */
    @GetMapping("/games/{gameId}/status")
    @ResponseStatus(HttpStatus.OK)
    public GameStatus getStatus(@PathVariable int gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            GameDisplayer.displayBoard(game);
            return new GameStatus(game);
        } catch (GameException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        } catch (Exception ex) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    /**
     * Get the pits for a given game.
     */
    @GetMapping("/games/{gameId}/pits")
    @ResponseStatus(HttpStatus.OK)
    public MoveCompleted getPits(@PathVariable int gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            GameDisplayer.displayBoard(game);
            return new MoveCompleted(game);
        } catch (GameException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        } catch (Exception ex) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    /**
     * Execute a move.
     */
    @PutMapping("/games/{gameId}/pits/{pitId}")
    @ResponseStatus(HttpStatus.OK)
    public MoveCompleted move(@RequestHeader("Player-Id") String playerId,
                              @PathVariable int gameId,
                              @PathVariable int pitId) {
        try {
            Player player = getPlayerByID(playerId);
            Game game = gameService.getGameById(gameId);

            Move move = new Move(game, player, pitId);
            gameRunner.executeMove(move);

            gameRunner.isGameComplete(game);

            gameService.saveGame(game);

            return new MoveCompleted(game);
        } catch (GameException ex) {
            throw new ResponseStatusException(ex.getStatus(), ex.getMessage());
        } catch (Exception ex) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    private Player getPlayerByID(String playerId) {
        if (isEmpty(playerId)) {
            throw new GameException("cannot determined player expected header Player-Id but none provided", BAD_REQUEST);
        }

        Player player = Player.getByID(playerId);
        if (player == null) {
            throw new GameException("invalid Player-Id header", BAD_REQUEST);
        }
        return player;
    }
}
