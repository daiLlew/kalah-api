package com.dai.llew.kalah.store;

import com.dai.llew.kalah.models.Game;

import java.util.List;

public interface GameStore {

    List<Game> getAllGames();

    long getNextGameID();

    void saveGame(Game game);

    Game getGameByID(long id);
}
