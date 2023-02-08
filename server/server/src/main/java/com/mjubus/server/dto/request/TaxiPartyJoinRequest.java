package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyJoinRequest {
    private Long memberId;

    public static TaxiPartyJoinRequest of(Long partyId) {
        return new TaxiPartyJoinRequest(partyId);
    }
}
