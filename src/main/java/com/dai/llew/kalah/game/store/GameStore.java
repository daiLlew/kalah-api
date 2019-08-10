package com.dai.llew.kalah.game.store;

import com.dai.llew.kalah.game.Game;

import java.util.List;

public interface GameStore {

    List<Game> getAllGames();

    long getNextGameID();

    void saveGame(Game game);

    Game getGameByID(long id);
}
