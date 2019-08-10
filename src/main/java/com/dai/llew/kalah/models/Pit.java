package com.dai.llew.kalah.models;

import static java.text.MessageFormat.format;

public class Pit {

    private static final int PLAYER_ONE_HOUSE_INDEX = 7;
    private static final int PLAYER_TWO_HOUSE_PIT_ID = 14;

    private int id;
    private int stoneCount;
    private boolean isHouse;

    public Pit(int id) {
        this.id = id;
        this.isHouse = (PLAYER_ONE_HOUSE_INDEX == id) || (PLAYER_TWO_HOUSE_PIT_ID == id);
        this.stoneCount = 6;
    }

    public int getId() {
        return this.id;
    }

    public boolean isHouse() {
        return this.isHouse;
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
