package com.mjubus.server.dto.busRoute;

import com.mjubus.server.domain.Station;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class RouteOrderDto {

    @ApiModelProperty(example = "1", dataType = "int")
    private Long id;

    @ApiModelProperty(example = "명지대 버스관리 사무소")
    private String name;

    @ApiModelProperty(example = "위도")
    private Double latitude;

    @ApiModelProperty(example = "경도")
    private Double longitude;

    @ApiModelProperty(example = "1", dataType = "int")
    private int route_order;

    public void setStation(Station station) {
        setId(station.getId());
        setName(station.getName());
        setLatitude(station.getLatitude());
        setLongitude(station.getLongitude());
    }
}
