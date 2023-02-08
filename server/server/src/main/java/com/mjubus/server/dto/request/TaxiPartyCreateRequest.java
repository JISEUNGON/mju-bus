package com.mjubus.server.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyCreateRequest {
    private Long administer;
    private Long taxiDestinationId;
    private Long min;
    private Long max;
    private Double meetingLatitude;
    private Double meetingLongitude;
    private String memo;
    private LocalDateTime endAt;

    public static TaxiPartyCreateRequest of(Long administer, Long taxiDestinationId, Long min, Long max, Double meetingLatitude, Double meetingLongitude, String memo, LocalDateTime endAt) {
        return new TaxiPartyCreateRequestBuilder()
                .administer(administer)
                .taxiDestinationId(taxiDestinationId)
                .min(min)
                .max(max)
                .meetingLatitude(meetingLatitude)
                .meetingLongitude(meetingLongitude)
                .memo(memo)
                .endAt(endAt)
                .build();
    }
}
