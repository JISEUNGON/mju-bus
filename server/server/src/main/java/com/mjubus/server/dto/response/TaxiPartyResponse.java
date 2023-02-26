package com.mjubus.server.dto.response;

import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.enums.TaxiPartyEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class TaxiPartyResponse {

    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @ApiModelProperty(example = "파티장")
    private MemberSimpleResponse administer;

    @ApiModelProperty(example = "만남장소", dataType = "String")
    private String meeting_place;

    @ApiModelProperty(example = "파티 현재 인원", dataType = "int")
    private Long curr_member;

    @ApiModelProperty(example = "파티 최대 인원", dataType = "int")
    private Long max_member;

    @ApiModelProperty(example = "모집 마감 시간", dataType = "datetime")
    private LocalDateTime end_at;

    @ApiModelProperty(example = "모집 상황", dataType = "int")
    private TaxiPartyEnum status;


    public static TaxiPartyResponse of(TaxiParty taxiParty, Long curr_member) {
        return TaxiPartyResponse.builder()
                .id(taxiParty.getId())
                .administer(MemberSimpleResponse.of(taxiParty.getAdminister()))
                .meeting_place(taxiParty.getMeeting_place())
                .curr_member(curr_member)
                .max_member(taxiParty.getMax())
                .end_at(taxiParty.getEndAt())
                .status(taxiParty.getStatus())
                .build();
    }
}
