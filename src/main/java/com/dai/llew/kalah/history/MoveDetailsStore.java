package com.dai.llew.kalah.history;

import com.dai.llew.kalah.game.MoveDetails;

import java.util.List;

public interface MoveDetailsStore {

    void recordMove(MoveDetails details);

    List<MoveDetails> getMoves(int gameId);
}
