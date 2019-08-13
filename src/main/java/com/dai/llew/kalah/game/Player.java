package com.dai.llew.kalah.game;

import org.apache.commons.lang3.StringUtils;

public enum Player {

    ONE("player-1", 7, 14),

    TWO("player-2", 14, 7);

    private final String id;
    private final int houseID;
    private final int opponentHouseID;

    Player(String id, int houseID, int opponentHouseID) {
        this.id = id;
        this.houseID = houseID;
        this.opponentHouseID = opponentHouseID;
    }

    public String getId() {
        return this.id;
    }

    public int getHousePitId() {
        return this.houseID;
    }

    public int oponentHouseID() {
        return this.opponentHouseID;
    }

    public static Player getByID(String id) {
        if (StringUtils.equals(id, ONE.getId())) {
            return ONE;
        }
        if (StringUtils.equals(id, TWO.getId())) {
            return TWO;
        }
        return null;
    }
}
