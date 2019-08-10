package com.dai.llew.kalah.game;

public enum Player {

    ONE(7, 14),

    TWO(14, 7);

    private final int houseID;
    private final int opponentHouseID;

    Player(int houseID, int opponentHouseID) {
        this.houseID = houseID;
        this.opponentHouseID = opponentHouseID;
    }

    public int getHouseID() {
        return this.houseID;
    }

    public int oponentHouseID() {
        return this.opponentHouseID;
    }
}
