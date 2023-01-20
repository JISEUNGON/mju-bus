package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StationRequest {
    private Long id;

    public static StationRequest of(String id) {
        return StationRequest.builder()
                .id(Long.parseLong(id))
                .build();
    }
}
