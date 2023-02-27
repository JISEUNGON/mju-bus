package com.mjubus.server.dto.response;

import com.mjubus.server.domain.TaxiDestination;
import com.mjubus.server.domain.TaxiParty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TaxiPartyDetailResponse {

    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @ApiModelProperty(example = "만나는 장소(경기 용인시 기흥구 구갈동 660-1)")
    private String meeting_place;

    @ApiModelProperty(example = "127.123456")
    private Double meeting_latitude;

    @ApiModelProperty(example = "37.123456")
    private Double meeting_longitude;

    @ApiModelProperty(example = "기흥역 3번 출구 앞에서 봐요 ~")
    private String memo;

    @ApiModelProperty(example = "최소 인원")
    private Long min_member;

    @ApiModelProperty(example = "최대 인원")
    private Long max_member;

    @ApiModelProperty(example = "택시 도착 정류장")
    private TaxiDestination destination;

    @ApiModelProperty(example = "모집 마감 시간", dataType = "datetime")
    private LocalDateTime end_at;

    public static TaxiPartyDetailResponse of(TaxiParty taxiParty) {
        return TaxiPartyDetailResponse.builder()
                .id(taxiParty.getId())
                .meeting_place(taxiParty.getMeeting_place())
                .meeting_latitude(taxiParty.getMeeting_latitude())
                .meeting_longitude(taxiParty.getMeeting_longitude())
                .memo(taxiParty.getMemo())
                .min_member(taxiParty.getMin())
                .max_member(taxiParty.getMax())
                .destination(taxiParty.getTaxi_destination_id())
                .end_at(taxiParty.getEndAt())
                .build();
    }
}
