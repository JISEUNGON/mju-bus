package com.mjubus.server.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyCreateRequest {

    @ApiModelProperty(value = "파티장 Member ID", example = "1")
    private Long administer;

    @ApiModelProperty(value = "택시 목적지 ID", example = "1")
    private Long taxiDestinationId;

    @ApiModelProperty(value = "최소 인원", example = "1")
    private Long min;

    @ApiModelProperty(value = "최대 인원", example = "4")
    private Long max;

    @ApiModelProperty(value = "만남 장소 위도")
    private Double meetingLatitude;

    @ApiModelProperty(value = "만남 장소 경도")
    private Double meetingLongitude;
    @ApiModelProperty(value = "장소 관련 간단한 메모")
    private String memo;

    @ApiModelProperty(value = "모집 마감 시간")
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
