package com.mjubus.server.exception.TaxiParty;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalPartyMembersStateException extends RuntimeException {
    private String message;

    public IllegalPartyMembersStateException(String message) {
        super(message);
        this.message = message;
    }
}
