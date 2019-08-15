package com.dai.llew.kalah.logging;

import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.model.Pit;

import java.util.List;

import static java.lang.String.format;

/**
 * Util for writing a game board/pits to the console useful for debugging.
 */
public class GameDisplayer {

    static final String SUMMARY_HEADER_FMT = "| %1$-10s| %2$-15s| %3$-10s|";
    static final String SUMMARY_SPACER_FMT = "+ %10s+ %15s+ %10s+";
    static final String PITS_FMT = " %1$-6s %2$-6s %3$-6s %4$-6s %5$-6s %6$-6s %7$-6s %8$-6s \n";
    static final String EMPTY = "";
    static final Object[] BOARDER = new Object[]{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};

    public static void displayBoard(Game game) {
        System.out.println("\nDEBUG\n");
        System.out.println(format(SUMMARY_HEADER_FMT, "Game ID", "Current Player", "State"));
        System.out.println(format(SUMMARY_SPACER_FMT, "", "", ""));
        System.out.println(format(SUMMARY_HEADER_FMT, game.getId(), game.getCurrentPlayer(), game.getState().name()));
        System.out.println(format(SUMMARY_SPACER_FMT, "", "", ""));
        System.out.println();
        System.out.println("Pits:");
        System.out.println(format(PITS_FMT, getPlayerTwoPits(game.getPits().getList())));
        System.out.println(format(PITS_FMT, getHousePits(game.getPits().getList())));
        System.out.println(format(PITS_FMT, getPlayerOnePits(game.getPits().getList())));
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
