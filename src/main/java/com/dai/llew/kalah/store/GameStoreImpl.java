package com.dai.llew.kalah.store;

import com.dai.llew.kalah.models.Game;
import com.dai.llew.kalah.store.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.text.MessageFormat.format;

@Component
public class GameStoreImpl implements GameStore {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private Map<Long, Game> store;

    public GameStoreImpl() {
        this.store = new HashMap<>();
    }

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(store.values());
    }

    @Override
    public long getNextGameID() {
        return ID_GENERATOR.getAndIncrement();
    }

    @Override
    public void saveGame(Game game) {
        store.put(game.getId(), game);
    }

    @Override
    public Game getGameByID(long id) {
        Game game = store.get(id);
        if (game == null)
            throw new NotFoundException(format("game ID: {0} not found", id));

        return game;
    }
}
