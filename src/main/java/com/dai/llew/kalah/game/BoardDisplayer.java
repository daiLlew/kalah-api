package com.dai.llew.kalah.game;

import java.util.List;

public class BoardDisplayer {

    static final String BOARD_FORMAT = "%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n";
    static final String BOARDER_SEP = "################################################################################";

    public static void displayBoard(Pits pits) {
        System.out.println(BOARDER_SEP);
        System.out.println("Board status:");
        System.out.format(BOARD_FORMAT, getPlayerTwoPits(pits));
        System.out.println();
        System.out.format(BOARD_FORMAT, getHousePits(pits));
        System.out.println();
        System.out.format(BOARD_FORMAT, getPlayerOnePits(pits));
    }

    private static Object[] getPlayerTwoPits(List<Pit> pits) {
        return new Object[]{"", pits.get(12), pits.get(11), pits.get(10), pits.get(9), pits.get(8), pits.get(7), ""};
    }

    private static Object[] getHousePits(List<Pit> pits) {
        return new Object[]{pits.get(13), "", "", "", "", "", "", pits.get(6)};
    }

    private static Object[] getPlayerOnePits(List<Pit> pits) {
        return new Object[]{"", pits.get(0), pits.get(1), pits.get(2), pits.get(3), pits.get(4), pits.get(5), ""};
    }
}
