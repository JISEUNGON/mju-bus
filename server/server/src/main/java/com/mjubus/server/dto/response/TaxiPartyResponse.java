package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiDestination;
import com.mjubus.server.domain.TaxiParty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class TaxiPartyResponse {

    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

//    @ApiModelProperty(example = "파티장")
//    private Member administer;

    @ApiModelProperty(example = "택시 도착 정류장")
    private TaxiDestination taxi_destination_id;

    @ApiModelProperty(example = "만남장소_위도", dataType = "double")
    private double meeting_latitude;

    @ApiModelProperty(example = "만남장소_경도", dataType = "double")
    private double meeting_longitude;

    @ApiModelProperty(example = "장소관련 간단한 메모", dataType = "char(30)")
    private String memo;

    @ApiModelProperty(example = "파티 최소 인원", dataType = "int")
    private Long min;

    @ApiModelProperty(example = "파티 최대 인원", dataType = "int")
    private Long max;

//    @ApiModelProperty(example = "모집 마감 시간", dataType = "datetime")
//    private LocalDateTime end_at;
//
//    @ApiModelProperty(example = "생성일", dataType = "datetime")
//    private LocalDateTime created_at;

//    @ApiModelProperty(example = "모집 상황", dataType = "int")
//    private Long status;

    public static TaxiPartyResponse of(Long id, TaxiDestination taxi_destination_id, double meeting_latitude, double meeting_longitude, String memo,
                                       Long min,Long max) {
        return TaxiPartyResponse.builder()
                .id(id)
                .taxi_destination_id(taxi_destination_id)
                .meeting_latitude(meeting_latitude)
                .meeting_longitude(meeting_longitude)
                .memo(memo)
                .min(min)
                .max(max)
                .build();
    }

    public static TaxiPartyResponse of(TaxiParty result) {
        return TaxiPartyResponse.builder()
                .id(result.getId())
                .taxi_destination_id(result.getTaxi_destination_id())
                .meeting_latitude(result.getMeeting_latitude())
                .meeting_longitude(result.getMeeting_longitude())
                .memo(result.getMemo())
                .min(result.getMin())
                .max(result.getMax())
                .build();
    }
}
