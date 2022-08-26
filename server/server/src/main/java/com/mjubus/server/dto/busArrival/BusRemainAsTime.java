package com.mjubus.server.dto.busArrival;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusRemainAsTime implements BusRemainInterface {

    @ApiModelProperty(example = "4", dataType = "long")
    private Long id;

    @ApiModelProperty(example = "진입로(명지대역)")
    private String name;

    @ApiModelProperty(example = "2022-08-26 17:25:32")
    private LocalDateTime expected_at;
}
