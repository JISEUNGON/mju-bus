package com.mjubus.server.exception.TaxiParty;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalPartyStateException extends RuntimeException {
    private String message;

    public IllegalPartyStateException(String message) {
        super(message);
        this.message = message;
    }
}
