package com.dai.llew.kalah.models;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    static final String BOARD_FORMAT = "%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n";

    private List<Pit> pits;

    public Board() {
        this.pits = IntStream.range(1, 15)
                .mapToObj(pitID -> new Pit(pitID))
                .collect(Collectors.toList());
    }

    public Pit getPit(int id) {
        return pits.get(id - 1);
    }

    public void displayBoard() {
        System.out.println("Board status:");
        System.out.format(BOARD_FORMAT, "", pits.get(0), pits.get(1), pits.get(2), pits.get(3), pits.get(4), pits.get(5), "");
        System.out.println();
        System.out.format(BOARD_FORMAT, pits.get(13), "", "", "", "", "", "", pits.get(6));
        System.out.println();
        System.out.format(BOARD_FORMAT, "", pits.get(7), pits.get(8), pits.get(9), pits.get(10), pits.get(11), pits.get(12), "");
        System.out.println("\n\n\n");
    }
}
