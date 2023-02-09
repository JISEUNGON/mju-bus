package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyDeleteRequest {
    private Long groupId;

    public static TaxiPartyDeleteRequest of(String groupId) {
        return new TaxiPartyDeleteRequest(Long.parseLong(groupId));
    }

}
