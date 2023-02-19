package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
public class BusResponse {
    @ApiModelProperty(example = "1", value = "고유 식별 ID")
    private Long id;

    @ApiModelProperty(example = "진입로(명지대행)", value = "버스 이름")
    private String name;

    @ApiModelProperty(example = "3500", value = "요금")
    private Long charge;

    public static BusResponse of(Long id, String name, Long charge) {
        return new BusResponse(id, name, charge);
    }

    public static BusResponse of(Bus bus) {
        return new BusResponse(bus.getId(), bus.getName(), bus.getCharge());
    }

    public static List<BusResponse> of(List<Bus> busList) {
        return busList.stream()
                .map(BusResponse::of)
                .collect(Collectors.toList());
    }
}

