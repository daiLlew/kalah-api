package com.dai.llew.kalah.game;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pits extends ArrayList<Pit> {

    public Pits() {
        super(IntStream.range(1, 15)
                .mapToObj(index -> new Pit(index))
                .collect(Collectors.toList()));
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
            throw new RuntimeException("invalid pit id");

        return pit.isEmpty();
    }
}
