package com.mjubus.server.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StationRadiusDetectedNameRequest {
    private Long groupId;

    public static StationRadiusDetectedNameRequest of(String groupId) {
        return new StationRadiusDetectedNameRequest(Long.parseLong(groupId));
    }
}
