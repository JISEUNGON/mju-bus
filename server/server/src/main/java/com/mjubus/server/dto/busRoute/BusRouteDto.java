package com.mjubus.server.dto.busRoute;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusRouteDto {
    @ApiModelProperty(example = "10", dataType = "int")
    private Long id;

    @ApiModelProperty(example = "명지대역")
    private String name;

    @ApiModelProperty(reference = "RouteOrderDto")
    private List<RouteOrderDto> stations;

    public void setBus(Bus bus) {
        setId(bus.getId());
        setName(bus.getName());
    }
}
