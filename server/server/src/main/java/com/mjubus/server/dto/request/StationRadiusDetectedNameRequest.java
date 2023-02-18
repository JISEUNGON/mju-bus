package com.mjubus.server.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StationRadiusDetectedNameRequest {
    private Double latitude;
    private Double longitude;

    public static StationRadiusDetectedNameRequest of(Double latitude, Double longitude) {
        return new StationRadiusDetectedNameRequest(latitude, longitude);
    }
}
