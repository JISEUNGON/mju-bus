package com.mjubus.server.dto.request;
import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiParty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TaxiPartyMembersRequest {

    @ApiModelProperty(example = "1", dataType = "int", value = "고유 식별 ID")
    private Long id;

    public static TaxiPartyMembersRequest of(String id) {
        return new TaxiPartyMembersRequest(Long.parseLong(id));
    }

    public static TaxiPartyMembersRequest of(Long id) {
        return new TaxiPartyMembersRequest(id);
    }
}
