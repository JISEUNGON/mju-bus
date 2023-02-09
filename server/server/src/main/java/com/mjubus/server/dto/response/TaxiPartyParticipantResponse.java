package com.mjubus.server.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaxiPartyParticipantResponse {
    Long partyParticipantCount;

    public static TaxiPartyParticipantResponse of(Long partyParticipantCount) {
        return TaxiPartyParticipantResponse.builder()
                .partyParticipantCount(partyParticipantCount)
                .build();
    }
}
