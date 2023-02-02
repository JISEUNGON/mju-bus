package com.mjubus.server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MjuAuthInfoResponse {
    @ApiModelProperty(example = "yes")
    private String isMjuUser;
}
