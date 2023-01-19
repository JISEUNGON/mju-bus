package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Station;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
@Getter
public class StationResponse {
    @ApiModelProperty(value = "정류장 식별번호", example = "1")
    private Long id;

    @ApiModelProperty(value = "정류장 이름", example = "명지대역")
    private String name;

    @ApiModelProperty(value = "정류장 위도", example = "37.230691")
    private Double latitude;

    @ApiModelProperty(value = "정류장 경도", example = "127.1986742")
    private Double longitude;

    public static StationResponse of(Long id, String name, Double latitude, Double longitude) {
        return StationResponse.builder()
                .id(id)
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static StationResponse of(Station station) {
        return StationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .latitude(station.getLatitude())
                .longitude(station.getLongitude())
                .build();
    }
}
