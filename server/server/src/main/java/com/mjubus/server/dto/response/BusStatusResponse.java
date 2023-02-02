package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Bus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
public class BusStatusResponse {
    public static final int BEFORE_RUNNING = 1;
    public static final int RUNNING = 2;
    public static final int FINISH_RUNNING = 3;

    @ApiModelProperty(example = "1", value = "고유 식별 ID")
    private Long id;

    @ApiModelProperty(example = "시내", value = "버스 이름")
    private String name;

    @ApiModelProperty(example = "3", value = "버스 운행 상태")
    private int status;

    // static method of
    public static BusStatusResponse of(Bus bus) {
        return new BusStatusResponse(
                bus.getId(),
                bus.getName(),
                FINISH_RUNNING
        );
    }

    public static BusStatusResponse of(Long id, String name, int status) {
        return new BusStatusResponse(
                id,
                name,
                status
        );
    }
}
