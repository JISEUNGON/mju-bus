package com.mjubus.server.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class StationRequest {
    private Long id;

    public static StationRequest of(String id) {
        return StationRequest.builder()
                .id(Long.parseLong(id))
                .build();
    }
}
