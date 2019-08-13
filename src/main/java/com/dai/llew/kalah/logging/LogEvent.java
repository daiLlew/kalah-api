package com.dai.llew.kalah.logging;

import com.dai.llew.kalah.game.Game;
import com.dai.llew.kalah.game.Pit;
import com.dai.llew.kalah.game.Player;
import com.github.onsdigital.logging.v2.event.BaseEvent;
import com.github.onsdigital.logging.v2.event.Severity;

import static com.github.onsdigital.logging.v2.DPLogger.logConfig;

public class LogEvent extends BaseEvent<LogEvent> {

    public static LogEvent info() {
        return new LogEvent(Severity.INFO);
    }

    public static LogEvent error() {
        return new LogEvent(Severity.ERROR);
    }

    private LogEvent(Severity severity) {
        super(logConfig().getNamespace(), severity, logConfig().getLogStore());
    }

    public LogEvent gameID(Game game) {
        if (null != game) {
            gameID(game.getId());
        }
        return this;
    }

    public LogEvent gameID(long gameId) {
        data("game_id", gameId);
        return this;
    }

    public LogEvent player(Player player) {
        if (null != player) {
            data("player", player.getId());
        }
        return this;
    }

    public LogEvent pit(Pit pit) {
        if (null != pit) {
            pit(pit.getId());
        }
        return this;
    }

    public LogEvent pit(int pitId) {
        data("pit_id", pitId);
        return this;
    }
}