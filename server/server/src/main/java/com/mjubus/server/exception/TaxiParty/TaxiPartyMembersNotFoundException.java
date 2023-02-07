package com.mjubus.server.exception.TaxiParty;

import com.mjubus.server.domain.TaxiPartyMembers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class TaxiPartyMembersNotFoundException extends RuntimeException{
    private String taxipartymembersId;

    public TaxiPartyMembersNotFoundException(Long id) {
        super(id.toString());

        taxipartymembersId = id.toString();
    }
}
