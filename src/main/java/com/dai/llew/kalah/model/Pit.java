package com.dai.llew.kalah.model;

import static java.text.MessageFormat.format;

public class Pit {

    static final int INITIAL_STONE_COUNT = 2;

    private int id;
    private int oppositeId;
    private int stoneCount;

    public Pit(int id, int oppositeId) {
        this.id = id;
        this.oppositeId = oppositeId;
        this.stoneCount = INITIAL_STONE_COUNT;
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

    public void addStones(int amount) {
        this.stoneCount += amount;
    }

    public int takeStones() {
        int taken = this.stoneCount;
        this.stoneCount = 0;
        return taken;
    }

    public boolean isEmpty() {
        return stoneCount == 0;
    }

    public Player getOwner() {
        if (id >= 1 && id <= 7)
            return Player.ONE;

        return Player.TWO;
    }

    public String toString() {
        return format("({0},{1})", id, stoneCount);
    }

    private boolean canPlaceStone(Player player) {
        return id != player.oponentHouseID();
    }

    public int getOppositeId() {
        return this.oppositeId;
    }
}
