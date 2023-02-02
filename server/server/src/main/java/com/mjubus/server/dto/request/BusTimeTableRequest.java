package com.mjubus.server.dto.request;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BusTimeTableRequest {

    @ApiModelProperty(example = "1", value = "고유 식별 ID")
    private Long id;

    public static BusTimeTableRequest of(Long id) {
        return new BusTimeTableRequest(id);
    }

    public static BusTimeTableRequest of(String id) {
        return new BusTimeTableRequest(Long.parseLong(id));
    }

    public static BusTimeTableRequest of(Bus bus) {
        return new BusTimeTableRequest(bus.getId());
    }

}
