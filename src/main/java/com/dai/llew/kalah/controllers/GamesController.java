package com.dai.llew.kalah.controllers;

import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.model.GameRunner;
import com.dai.llew.kalah.model.Move;
import com.dai.llew.kalah.model.Player;
import com.dai.llew.kalah.responses.GameCreatedResponse;
import com.dai.llew.kalah.responses.GameStatusResponse;
import com.dai.llew.kalah.responses.MoveCompletedResponse;
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

@RestController
public class GamesController {

    @Autowired
    private GameRunner gameRunner;

    @Autowired
    private GameService gameService;

    /**
     * Create a new game
     */
    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public GameCreatedResponse newGame() {
        long gameID = gameService.createNewGame();
        return new GameCreatedResponse(gameID);
    }

    /**
     * Get all games.
     */
    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGames() {
        return gameService.getGames();
    }

    /**
     * Get the status of the specified game.
     */
    @GetMapping("/games/{gameId}/status")
    @ResponseStatus(HttpStatus.OK)
    public GameStatusResponse getStatus(@PathVariable int gameId) {
        Game game = gameService.getGameById(gameId);
        return new GameStatusResponse(game);
    }

    /**
     * Get the pits for a given game.
     */
    @GetMapping("/games/{gameId}/pits")
    @ResponseStatus(HttpStatus.OK)
    public MoveCompletedResponse getPits(@PathVariable int gameId) {
        Game game = gameService.getGameById(gameId);
        return new MoveCompletedResponse(game);
    }

    /**
     * Execute a move.
     */
    @PutMapping("/games/{gameId}/pits/{pitId}")
    @ResponseStatus(HttpStatus.OK)
    public MoveCompletedResponse move(@RequestHeader("Player-Id") String playerId,
                                      @PathVariable int gameId,
                                      @PathVariable int pitId) {
        Player player = getPlayerByID(playerId);
        Game game = gameService.getGameById(gameId);

        Move move = new Move(game, player, pitId);
        gameRunner.executeMove(move);

        gameRunner.isGameComplete(game);

        gameService.saveGame(game);
        return new MoveCompletedResponse(game);
    }

    private Player getPlayerByID(String playerId) {
        if (isEmpty(playerId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no player id headrer provided");
        }

        Player player = Player.getByID(playerId);
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid player id header");
        }
        return player;
    }
}
