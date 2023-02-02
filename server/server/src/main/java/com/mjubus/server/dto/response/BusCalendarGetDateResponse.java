package com.mjubus.server.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusCalendarGetDateResponse {
    @ApiModelProperty(example = "2022-08-01T22:00:00.000044", value = "2022-08-01T22:00:00.000044")
    private LocalDateTime time;

    public static BusCalendarGetDateResponse of(LocalDateTime now) {
        return new BusCalendarGetDateResponse(now);
    }
}
