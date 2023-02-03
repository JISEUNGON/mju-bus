package com.mjubus.server.dto.response;

import com.mjubus.server.domain.TaxiParty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaxiPartyResponse {

    @ApiModelProperty(example = "파티장", dataType = "int")
    private long administer;

    @ApiModelProperty(example = "택시 도착 정류장", dataType = "int")
    private long taxi_destination_id;

    @ApiModelProperty(example = "만남장소_위도", dataType = "double")
    private double meeting_latitude;

    @ApiModelProperty(example = "만남장소_경도", dataType = "double")
    private double meeting_longitude;

    @ApiModelProperty(example = "장소관련 간단한 메모", dataType = "char(30)")
    private String memo;

    @ApiModelProperty(example = "파티 최소 인원", dataType = "int")
    private long min;

    @ApiModelProperty(example = "파티 최대 인원", dataType = "int")
    private long max;

    @ApiModelProperty(example = "모집 마감 시간", dataType = "datetime")
    private long end_at;

    @ApiModelProperty(example = "생성일", dataType = "datetime")
    private long created_at;

    @ApiModelProperty(example = "모집 상황", dataType = "int")
    private long status;

    public static TaxiPartyResponse of(long administer, long taxi_destination_id, double meeting_latitude,
                                  double meeting_longitude, String memo, long min, long max, long end_at, long created_at, long status) {
        return TaxiPartyResponse.builder()
                .administer(administer)
                .taxi_destination_id(taxi_destination_id)
                .meeting_latitude(meeting_latitude)
                .meeting_longitude(meeting_longitude)
                .memo(memo)
                .min(min)
                .max(max)
                .end_at(end_at)
                .created_at(created_at)
                .status(status)
                .build();
    }

    public static TaxiPartyResponse of(TaxiParty result) {
        return TaxiPartyResponse.builder()
                .administer(result.getAdminister())
                .taxi_destination_id(result.getTaxi_destination_id())
                .meeting_latitude(result.getMeeting_latitude())
                .meeting_longitude(result.getMeeting_longitude())
                .memo(result.getMemo())
                .min(result.getMin())
                .max(result.getMax())
                .end_at(result.getEnd_at())
                .created_at(result.getCreated_at())
                .status(result.getStatus())
                .build();
    }
}
