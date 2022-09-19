package com.mjubus.server.dto.stationPath;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PathDto {
    @ApiModelProperty(example = "37.230473", dataType = "double")
    private Double latitude;

    @ApiModelProperty(example = "127.187983", dataType = "double")
    private Double longitude;

    @ApiModelProperty(example = "1", dataType = "int")
    private int path_order;
}
