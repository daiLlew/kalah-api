package com.dai.llew.kalah.store;

import com.dai.llew.kalah.exceptions.GameException;
import com.dai.llew.kalah.model.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.text.MessageFormat.format;

@Component
public class GameStoreImpl implements GameStore {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private Map<Integer, Game> store;

    public GameStoreImpl() {
        this.store = new HashMap<>();

        // TODO REMOVE ONCE BEFORE SUBMITTING
        saveGame(createExample());
    }

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(store.values());
    }

    @Override
    public int getNextGameID() {
        return ID_GENERATOR.getAndIncrement();
    }

    @Override
    public void saveGame(Game game) {
        store.put(game.getId(), game);
    }

    @Override
    public Game getGameByID(int id) {
        Game game = store.get(id);
        if (game == null)
            throw new GameException(format("game ID: {0} not found", id));

        return game;
    }

    private Game createExample() {
        Game g = new Game(getNextGameID());
        g.getPits().stream().forEach(p -> p.takeStones());

        g.getPits().getPitByID(5).addStones(1);
        g.getPits().getPitByID(8).addStones(1);

        return g;
    }
}
