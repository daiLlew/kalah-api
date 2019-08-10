package com.dai.llew.kalah.game;

import static com.dai.llew.kalah.game.BoardDisplayer.displayBoard;

public class Game {

    private long id;
    private Pits pits;

    public Game(long id) {
        this.id = id;
        this.pits = new Pits();
    }

    public long getId() {
        return this.id;
    }

    public Pits getPits() {
        return this.pits;
    }

    public void makeMove(int targetPitID, Player player) {
        displayBoard(pits);
        Pit pit = pits.getPitByID(targetPitID);

        int pitID = targetPitID;
        int stonesAvailable = pit.takeStones();

        while (stonesAvailable > 0) {
            pitID = getNextPitID(pitID);
            Pit next = pits.getPitByID(pitID);
            if (next.addStone(player)) {
                stonesAvailable--;
            }
        }

        displayBoard(pits);
        System.out.println();
        System.out.format("Debug: Last placement: %d, Last placement count: %d\n", pitID, pits.getPitByID(pitID).getStoneCount());
        System.out.println();
    }

    private int getNextPitID(int current) {
        if (current >= 14)
            return 1;
        return current + 1;
    }
}
