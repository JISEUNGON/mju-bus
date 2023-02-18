package com.mjubus.server.exception.Station;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StationLocationNotFound extends RuntimeException {
    private String message;

    public StationLocationNotFound(String message) {
        super(message);
        this.message = message;
    }
}
