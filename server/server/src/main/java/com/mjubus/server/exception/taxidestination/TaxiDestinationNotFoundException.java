package com.mjubus.server.exception.taxidestination;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaxiDestinationNotFoundException extends RuntimeException {
    private String message;

    public TaxiDestinationNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
