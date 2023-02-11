package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxiPartyRequest {

    @ApiModelProperty(example = "1", dataType = "int", value = "고유 식별 ID")
    private Long id;

    public static TaxiPartyRequest of(String id) {
        return new TaxiPartyRequest(Long.parseLong(id));
    }

    public static TaxiPartyRequest of(Long id) {
        return new TaxiPartyRequest(id);
    }
}
