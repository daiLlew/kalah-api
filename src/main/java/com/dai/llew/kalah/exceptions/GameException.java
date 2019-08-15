package com.dai.llew.kalah.exceptions;

import org.springframework.http.HttpStatus;

public class GameException extends RuntimeException {

    private HttpStatus status;

    public GameException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
