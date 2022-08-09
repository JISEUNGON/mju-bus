package com.mjubus.server.exception;

public class BusCalenderNotFoundException extends RuntimeException {
    private String message;
    public BusCalenderNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
