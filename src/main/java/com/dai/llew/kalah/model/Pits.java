package com.dai.llew.kalah.model;

import com.dai.llew.kalah.exceptions.GameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class Pits extends ArrayList<Pit> {

    private static final Predicate<Pit> FILTER_P1_PITS = (p -> p.getId() >= 1 && p.getId() <= 6);
    private static final Predicate<Pit> FILTER_P2_PITS = (p -> p.getId() >= 8 && p.getId() <= 13);

    public Pits() {
        super(createPitList());
    }

    public Pit getPitByID(int pitId) {
        Optional<Pit> result = this.stream()
                .filter(pit -> pit.getId() == pitId)
                .findFirst();

        if (result.isPresent())
            return result.get();
        return null;
    }

    public Map<Integer, Pit> getAsMap() {
        return stream()
                .collect(Collectors.toMap(
                        pit -> pit.getId(),
                        pit -> pit));
    }

    public boolean isPitEmpty(int pitId) {
        Pit pit = getPitByID(pitId);
        if (pit == null)
            throw new GameException("invalid pit id", BAD_REQUEST);

        return pit.isEmpty();
    }

    public void addStonesToPlayerHouse(Player player, int amount) {
        Pit pit = getPitByID(player.getHousePitId());
        pit.addStones(amount);
    }

    public int stonesRemainingInPlayerPits(Player player) {
        Predicate<Pit> predicateFilter = FILTER_P1_PITS;

        if (Player.TWO.equals(player)) {
            predicateFilter = FILTER_P2_PITS;
        }

        return stream()
                .filter(predicateFilter)
                .mapToInt(p -> p.getStoneCount())
                .sum();
    }

    public int getPlayerFinalScore(Player player) {
        int houseScore = getPitByID(player.getHousePitId()).getStoneCount();
        return houseScore + stonesRemainingInPlayerPits(player);
    }

    private static List<Pit> createPitList() {
        return new ArrayList<Pit>() {{
            add(new Pit(1, 13));
            add(new Pit(2, 12));
            add(new Pit(3, 11));
            add(new Pit(4, 10));
            add(new Pit(5, 9));
            add(new Pit(6, 8));
            add(new Pit(7, 7));
            add(new Pit(8, 6));
            add(new Pit(9, 5));
            add(new Pit(10, 4));
            add(new Pit(11, 3));
            add(new Pit(12, 2));
            add(new Pit(13, 1));
            add(new Pit(14, 14));
        }};
    }
}
