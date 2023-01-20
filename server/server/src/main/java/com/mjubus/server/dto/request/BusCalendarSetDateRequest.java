package com.mjubus.server.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BusCalendarSetDateRequest {
    @ApiModelProperty(example = "2022-08-01T22:00:00.000044", value = "2022-08-01T22:00:00.000044")
    private LocalDateTime time;

    public static BusCalendarSetDateRequest of(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        return new BusCalendarSetDateRequest(localDateTime);
    }

    public static BusCalendarSetDateRequest of(LocalDateTime date) {
        return new BusCalendarSetDateRequest(date);
    }
}
