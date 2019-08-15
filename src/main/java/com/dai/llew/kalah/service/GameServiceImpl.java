package com.dai.llew.kalah.service;

import com.dai.llew.kalah.exceptions.GameException;
import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.store.GameStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.dai.llew.kalah.logging.LogEvent.error;
import static com.dai.llew.kalah.logging.LogEvent.info;

@Component
public class GameServiceImpl implements GameService {

    @Autowired
    private GameStore store;

    @Override
    public int createNewGame() {
        int id = store.getNextGameID();
        Game game = new Game(id);
        saveGame(game);
        info().gameID(game).log("new model created successfully");
        return game.getId();
    }

    @Override
    public Game getGameById(int gameId) {
        try {
            return store.getGameByID(gameId);
        } catch (GameException ex) {
            error().exception(ex)
                    .gameID(gameId)
                    .log("error getting model by id");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            error().exception(ex)
                    .gameID(gameId)
                    .log("error getting model by id");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Game> getGames() {
        return store.getAllGames();
    }

    @Override
    public void saveGame(Game game) {
        try {
            store.saveGame(game);
            info().gameID(game)
                    .log("save model completed successfully");
        } catch (Exception ex) {
            error().gameID(game)
                    .exception(ex)
                    .log("error saving model");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
