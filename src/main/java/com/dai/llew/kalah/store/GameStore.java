package com.dai.llew.kalah.store;

import com.dai.llew.kalah.game.Game;

import java.util.List;

public interface GameStore {

    List<Game> getAllGames();

    int getNextGameID();

    void saveGame(Game game);

    Game getGameByID(long id);
}
