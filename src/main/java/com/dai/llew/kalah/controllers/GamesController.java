package com.dai.llew.kalah.controllers;

import com.dai.llew.kalah.exceptions.GameException;
import com.dai.llew.kalah.game.Game;
import com.dai.llew.kalah.game.Player;
import com.dai.llew.kalah.responses.GameCreatedResponse;
import com.dai.llew.kalah.responses.GameStatusResponse;
import com.dai.llew.kalah.responses.MoveCompletedResponse;
import com.dai.llew.kalah.store.GameStore;
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

import static com.dai.llew.kalah.logging.LogEvent.error;
import static com.dai.llew.kalah.logging.LogEvent.info;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@RestController
public class GamesController {

    @Autowired
    private GameStore gameStore;

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public GameCreatedResponse newGame() {
        long gameID = createNewGame();
        return new GameCreatedResponse(gameID);
    }

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGames() {
        return gameStore.getAllGames();
    }

    @GetMapping("/games/{gameId}/status")
    @ResponseStatus(HttpStatus.OK)
    public GameStatusResponse getStatus(@PathVariable long gameId) {
        return new GameStatusResponse(getGameById(gameId));
    }

    @GetMapping("/games/{gameId}/pits")
    @ResponseStatus(HttpStatus.OK)
    public MoveCompletedResponse getPits(@PathVariable long gameId) {
        Game game = getGameById(gameId);
        return new MoveCompletedResponse(game);
    }

    @PutMapping("/games/{gameId}/pits/{pitId}")
    @ResponseStatus(HttpStatus.OK)
    public MoveCompletedResponse move(@RequestHeader("Player-Id") String playerId,
                                      @PathVariable long gameId,
                                      @PathVariable int pitId) {
        Player player = getPlayerByID(playerId);
        Game game = getGameById(gameId);
        executePlayerMove(game, player, pitId);
        saveGame(game);
        return new MoveCompletedResponse(game);
    }

    private long createNewGame() {
        int id = gameStore.getNextGameID();
        Game game = new Game(id);
        saveGame(game);
        info().gameID(game).log("new game created successfully");
        return game.getId();
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

    private Game getGameById(long gameId) {
        try {
            return gameStore.getGameByID(gameId);
        } catch (GameException ex) {
            error().exception(ex)
                    .gameID(gameId)
                    .log("error getting game by id");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            error().exception(ex)
                    .gameID(gameId)
                    .log("error getting game by id");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveGame(Game game) {
        try {
            gameStore.saveGame(game);
            info().gameID(game)
                    .log("save game completed successfully");
        } catch (Exception ex) {
            error().gameID(game)
                    .exception(ex)
                    .log("error saving game");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void executePlayerMove(Game game, Player player, int pitId) {
        try {
            info().gameID(game).player(player).pit(pitId).log("attempting to execute player move");
            game.executePlayerMove(pitId, player);
            game.checkForWinner();
            info().gameID(game)
                    .player(player)
                    .pit(pitId)
                    .log("player move completed successfully");
        } catch (GameException ex) {
            error().gameID(game)
                    .player(player)
                    .pit(pitId)
                    .log(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            error().gameID(game)
                    .player(player)
                    .pit(pitId)
                    .exception(ex)
                    .log("error executing player move");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
