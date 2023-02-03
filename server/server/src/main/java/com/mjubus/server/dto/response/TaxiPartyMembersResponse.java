package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiPartyMembers;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class TaxiPartyMembersResponse {

    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @ApiModelProperty(example = "파티 멤버")
    private Member member;

//    @ApiModelProperty(example = "파티 ID")
//    private TaxiParty taxiPartyId;

    public static TaxiPartyMembersResponse of (Long id, Member member){
        return TaxiPartyMembersResponse.builder()
                .id(id)
                .member(member)
                .build();
    }

    public static TaxiPartyMembersResponse of (TaxiPartyMembers result){
        return TaxiPartyMembersResponse.builder()
                .id(result.getId())
                .member(result.getMember())
                .build();
    }
}
