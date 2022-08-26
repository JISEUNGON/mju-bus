package com.mjubus.server.dto.busArrival;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusRemainAsSecond implements BusRemainInterface {
    @ApiModelProperty(example = "4", dataType = "long")
    private Long id;

    @ApiModelProperty(example = "진입로(명지대역)")
    private String name;

    @ApiModelProperty(example = "254", dataType = "int")
    private int remains;
}
