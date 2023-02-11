package com.mjubus.server.exception.TaxiParty;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class TaxiPartyNotFoundException extends RuntimeException{
    private String taxipartyId;

    public TaxiPartyNotFoundException(Long id) {
        super(id.toString());

        taxipartyId = id.toString();
    }

}
