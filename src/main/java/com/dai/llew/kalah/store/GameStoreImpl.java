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
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * For the simplicity and to guaranteed this app work straight of the box I decided to mock the behaviour of a database
 * by using an in memory hashmap to store games between requests. Obviously the data is not persisted so if the app
 * stops you loose your game but for demo puposes I felt this was enough.
 * <p>
 * If this was for real then I would use an actual database and based on the needs of the apps either use the
 * simple JDBC Template or if it made sense consider using the JPA annotations and create entity classes.
 * <p>
 * My inital choice/preference would be a NoSQL implementation like MongoDB.
 */
@Component
public class GameStoreImpl implements GameStore {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private Map<Integer, Game> store;

    public GameStoreImpl() {
        this.store = new HashMap<>();
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
            throw new GameException(format("game ID: {0} not found", id), BAD_REQUEST);

        return game;
    }

    /**
     * For debugging.
     */
    private Game createExample() {
        Game g = new Game(getNextGameID());
        g.getPits().getList().stream().forEach(p -> p.takeStones());

        g.getPits().getPitByID(5).addStones(1);
        g.getPits().getPitByID(8).addStones(1);

        return g;
    }
}
