package com.mjubus.server.dto.response;


import com.mjubus.server.dto.busArrival.BusRemainInterface;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusArrivalResponse {

    @ApiModelProperty(example = "4", dataType = "int")
    private Long id;

    @ApiModelProperty(example = "진입로(명지대행)")
    private String name;

    @ApiModelProperty(example = "2022-08-26 17:06:52")
    private LocalDateTime response_at;

    private List<? extends BusRemainInterface> busList;
}
