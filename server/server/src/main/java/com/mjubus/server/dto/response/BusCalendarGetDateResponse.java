package com.mjubus.server.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BusCalendarGetDateResponse {
    @ApiModelProperty(example = "2022-08-01T22:00:00.000044", value = "2022-08-01T22:00:00.000044")
    private LocalDateTime time;

    public static BusCalendarGetDateResponse of(LocalDateTime now) {
        return new BusCalendarGetDateResponse(now);
    }
}
