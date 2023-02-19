package com.mjubus.server.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TaxiPartyMembersListResponse {

    private Long administer;
    private List<TaxiPartyMembersResponse> taxiPartyMembersList;

    public static TaxiPartyMembersListResponse of(Long administer, List<TaxiPartyMembersResponse> taxiPartyMembersList){
        return new TaxiPartyMembersListResponse(administer, taxiPartyMembersList);
    }
}
