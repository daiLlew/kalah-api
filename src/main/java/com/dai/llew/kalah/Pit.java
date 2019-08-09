package com.dai.llew.kalah;

import static java.text.MessageFormat.format;

public class Pit {

    private static final int PLAYER_ONE_KALAH_PIT_ID = 7;
    private static final int PLAYER_TWO_KALAH_PIT_ID = 14;

    private long id;
    private int stoneCount;
    private boolean isKalah;

    public Pit(long id) {
        this.id = id;
        this.isKalah = (PLAYER_ONE_KALAH_PIT_ID == id) || (PLAYER_TWO_KALAH_PIT_ID == id);
        this.stoneCount = 6;
    }

    public long getId() {
        return this.id;
    }

    public boolean isKalah() {
        return this.isKalah;
    }

    public int getStoneCount() {
        return this.stoneCount;
    }

    public void addStone() {
        this.stoneCount++;
    }

    public int takeStones() {
        int taken = this.stoneCount;
        this.stoneCount = 0;
        return taken;
    }

    public String toString() {
        return format("({0},{1})", id, stoneCount);
    }
}
