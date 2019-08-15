package com.dai.llew.kalah.service;

import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.store.GameStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return store.getGameByID(gameId);
    }

    @Override
    public List<Game> getGames() {
        return store.getAllGames();
    }

    @Override
    public void saveGame(Game game) {
        store.saveGame(game);
        info().gameID(game).log("save model completed successfully");
    }
}
