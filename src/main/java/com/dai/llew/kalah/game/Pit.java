package com.dai.llew.kalah.game;

import static java.text.MessageFormat.format;

public class Pit {

    private int id;
    private int stoneCount;

    public Pit(int id) {
        this.id = id;
        this.stoneCount = 6;
    }

    public int getId() {
        return this.id;
    }

    public int getStoneCount() {
        return this.stoneCount;
    }

    public boolean addStone(Player player) {
        if (canPlaceStone(player)) {
            this.stoneCount++;
            return true;
        }
        return false;
    }

    public int takeStones() {
        int taken = this.stoneCount;
        this.stoneCount = 0;
        return taken;
    }

    public String toString() {
        return format("({0},{1})", id, stoneCount);
    }

    private boolean canPlaceStone(Player player) {
        return id != player.oponentHouseID();
    }
}
