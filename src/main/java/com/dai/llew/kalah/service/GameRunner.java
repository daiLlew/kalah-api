package com.dai.llew.kalah.service;

import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.model.Move;

public interface GameRunner {

    void executeMove(Move move);

    boolean isGameComplete(Game game);
}
