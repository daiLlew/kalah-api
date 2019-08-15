package com.dai.llew.kalah.store;

import com.dai.llew.kalah.model.Game;

import java.util.List;

public interface GameStore {

    List<Game> getAllGames();

    int getNextGameID();

    void saveGame(Game game);

    Game getGameByID(int id);
}
