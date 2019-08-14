package com.dai.llew.kalah.history;

import com.dai.llew.kalah.game.MoveDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MoveDetailsStoreImpl implements MoveDetailsStore {

    private Map<Integer, List<MoveDetails>> store;

    public MoveDetailsStoreImpl() {
        this.store = new HashMap<>();
    }

    @Override
    public void recordMove(MoveDetails details) {
        List<MoveDetails> movesHistory = store.getOrDefault(details.getGameId(), new ArrayList<>());
        movesHistory.add(details);
        store.put(details.getGameId(), movesHistory);
    }

    @Override
    public List<MoveDetails> getMoves(int gameId) {
        return store.getOrDefault(gameId, new ArrayList<>());
    }
}
