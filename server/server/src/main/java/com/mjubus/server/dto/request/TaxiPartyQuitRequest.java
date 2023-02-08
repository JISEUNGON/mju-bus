package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyQuitRequest {
    private String memberId;
    public static TaxiPartyQuitRequest of(String memberId) {
        return new TaxiPartyQuitRequest(memberId);
    }
}
