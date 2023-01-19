package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Bus;
import com.mjubus.server.dto.busRoute.RouteOrderDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BusRouteResponse {
    @ApiModelProperty(example = "10", dataType = "int", value = "버스 고유 식별 ID")
    private Long id;

    @ApiModelProperty(example = "명지대역", value = "버스 이름")
    private String name;

    @ApiModelProperty(reference = "RouteOrderDto", value = "버스 노선")
    private List<RouteOrderDto> stations;

    public void setBus(Bus bus) {
        setId(bus.getId());
        setName(bus.getName());
    }
}
