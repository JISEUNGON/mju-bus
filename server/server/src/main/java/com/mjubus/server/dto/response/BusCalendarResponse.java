package com.mjubus.server.dto.response;

import com.mjubus.server.domain.BusCalendar;
import com.mjubus.server.util.DateHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BusCalendarResponse {
    @ApiModelProperty(example = "1", value = "고유 식별 ID")
    private Long id;

    @ApiModelProperty(example = "겨울 방학", value = "겨울 방학")
    private String name;

    @ApiModelProperty(example = "2022-08-01T22:00:00.000044", value = "2022-08-01T22:00:00.000044")
    private LocalDateTime time;

    public static BusCalendarResponse of(BusCalendar busCalendar) {
        return new BusCalendarResponse(
                busCalendar.getId(),
                busCalendar.getName(),
                DateHandler.getToday()
        );
    }
}
