package com.dai.llew.kalah.controllers;

import com.dai.llew.kalah.game.Game;
import com.dai.llew.kalah.game.Player;
import com.dai.llew.kalah.game.exceptions.NotFoundException;
import com.dai.llew.kalah.game.store.GameStore;
import com.dai.llew.kalah.responses.GameCreatedResponse;
import com.dai.llew.kalah.responses.GameStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class GamesController {

    private static Logger LOGGER = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    private GameStore gameStore;

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public GameCreatedResponse newGame() {
        long id = gameStore.getNextGameID();
        Game game = new Game(id);
        gameStore.saveGame(game);

        return new GameCreatedResponse(id);
    }

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGames() {
        return gameStore.getAllGames();
    }

    @GetMapping("/games/{gameId}/pits")
    @ResponseStatus(HttpStatus.OK)
    public GameStatusResponse getPits(@PathVariable long gameId) {
        Game game = getGameById(gameId);
        return new GameStatusResponse(game);
    }

    @PutMapping("/games/{gameId}/pits/{pitId}")
    @ResponseStatus(HttpStatus.OK)
    public GameStatusResponse move(@PathVariable long gameId, @PathVariable int pitId) {
        Game game = getGameById(gameId);
        game.makeMove(pitId, Player.ONE);
        gameStore.saveGame(game);

        return new GameStatusResponse(game);
    }

    private Game getGameById(long gameId) {
        try {
            return gameStore.getGameByID(gameId);
        } catch (NotFoundException ex) {
            LOGGER.error("error gettig game by id", ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("error gettig game by id", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");
        }
    }
}
