package com.mjubus.server.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TaxiPartyListResponse {

    private List<TaxiPartyResponse> taxiPartyList;

    public static TaxiPartyListResponse of(List<TaxiPartyResponse> taxiPartyList) {
        return new TaxiPartyListResponse(taxiPartyList);
    }
}
