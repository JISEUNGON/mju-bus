package com.mjubus.server.exception;

import java.time.LocalDateTime;

public class BusCalenderNotFoundException extends RuntimeException {
    private String message;
    public BusCalenderNotFoundException(LocalDateTime dateTime) {
        super(dateTime.toString());
        this.message = dateTime.toString();
    }
}
