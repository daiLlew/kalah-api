package com.dai.llew.kalah.service;

import com.dai.llew.kalah.model.Game;

import java.util.List;

public interface GameService {

    int createNewGame();

    Game getGameById(int gameId);

    List<Game> getGames();

    void saveGame(Game game);
}
