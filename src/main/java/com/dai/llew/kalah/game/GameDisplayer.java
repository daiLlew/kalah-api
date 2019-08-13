package com.dai.llew.kalah.game;

import java.util.List;

public class GameDisplayer {

    static final String FMT = "%1$-10s%2$-10s%3$-10s%4$-10s%5$-10s%6$-10s%7$-10s%8$-10s\n";
    static final String H1_FMT = "%1$-10s%2$-10s\n";

    static final String EMPTY = " - ";
    static final Object[] BOARDER = new Object[]{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};

    public static void displayBoard(Game game, Player player) {

        System.out.println();
        System.out.format(H1_FMT, "Game ID", "Player ID");
        System.out.format(H1_FMT,game.getId(),  player.getId());
        System.out.println();

        System.out.format(FMT, getPlayerTwoPits(game.getPits()));
        System.out.format(FMT, getHousePits(game.getPits()));
        System.out.format(FMT, getPlayerOnePits(game.getPits()));
        System.out.println();
    }

    private static Object[] getPlayerTwoPits(List<Pit> pits) {
        return new Object[]{EMPTY, pits.get(12), pits.get(11), pits.get(10), pits.get(9), pits.get(8), pits.get(7), EMPTY};
    }

    private static Object[] getHousePits(List<Pit> pits) {
        return new Object[]{pits.get(13), EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, pits.get(6)};
    }

    private static Object[] getPlayerOnePits(List<Pit> pits) {
        return new Object[]{EMPTY, pits.get(0), pits.get(1), pits.get(2), pits.get(3), pits.get(4), pits.get(5), EMPTY};
    }
}
