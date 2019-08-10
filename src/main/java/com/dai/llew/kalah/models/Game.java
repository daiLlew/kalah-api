package com.dai.llew.kalah.models;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    static final String BOARD_FORMAT = "%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n";

    private long id;

    private Map<Integer, Pit> pits;

    private GameStatus status;

    public Game(long id) {
        this.id = id;
        this.pits = IntStream.range(1, 15)
                .mapToObj(index -> new Pit(index))
                .collect(Collectors.toMap(
                        pit -> pit.getId(),
                        pit -> pit
                ));
        this.status = GameStatus.CREATED;
    }

    public long getId() {
        return this.id;
    }

    public Map<Integer, Pit> getPits() {
        return this.pits;
    }

    public GameStatus getStatus() {
        return this.status;
    }

    public Pit getPit(int id) {
        return pits.get(id - 1);
    }

    public void displayBoard() {
        System.out.println();
        System.out.println("Board status:");
        System.out.format(BOARD_FORMAT, getPlayerTwoPits());
        System.out.println();
        System.out.format(BOARD_FORMAT, getHousePits());
        System.out.println();
        System.out.format(BOARD_FORMAT, getPlayerOnePits());
        System.out.println();
    }

    private Object[] getPlayerOnePits() {
        return new Object[]{"", pits.get(1), pits.get(2), pits.get(3), pits.get(4), pits.get(5), pits.get(6), ""};
    }

    private Object[] getPlayerTwoPits() {
        return new Object[]{"", pits.get(7), pits.get(8), pits.get(9), pits.get(10), pits.get(11), pits.get(12), ""};
    }

    private Object[] getHousePits() {
        return new Object[]{pits.get(13), "", "", "", "", "", "", pits.get(6)};
    }
}
