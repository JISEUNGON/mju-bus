package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyQuitRequest {
    private String groupId;
    public static TaxiPartyQuitRequest of(String groupId) {
        return new TaxiPartyQuitRequest(groupId);
    }
}
