package com.mjubus.server.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TaxiPartyMembersListResponse {

    private List<TaxiPartyMembersResponse> taxiPartyMembersList;

    public static TaxiPartyMembersListResponse of(List<TaxiPartyMembersResponse> taxiPartyMembersList){
        return new TaxiPartyMembersListResponse(taxiPartyMembersList);
    }
}
