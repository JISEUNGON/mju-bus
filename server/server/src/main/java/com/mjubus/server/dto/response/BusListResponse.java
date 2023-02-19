package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BusListResponse {

    @ApiModelProperty(example = "시내 버스", value = "시내버스 리스트")
    private List<BusResponse> sine_bus_list;

    @ApiModelProperty(example = "시외 버스", value = "시외버스 리스트")
    private List<BusResponse> siwe_bus_list;

    public static BusListResponse of(List<Bus> sineBusList, List<Bus> siwaBusList) {
        return BusListResponse.builder()
                .sine_bus_list(BusResponse.of(sineBusList))
                .siwe_bus_list(BusResponse.of(siwaBusList))
                .build();
    }
}
