package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "버스 도착 정보")
@Table(name="bus_arrival")
@Getter
@Setter
public class BusArrival {
    @Id
    @Column(name ="sid", columnDefinition = "char(36)")
    @ApiModelProperty(example = "고유 식별 SID")
    private String sid;

    @Column(name = "pre_bus_arrival_sid", columnDefinition = "char(36)")
    private String preBusArrivalSid;

    @JoinColumn(name = "station_id", columnDefinition = "int")
    @ApiModelProperty(example = "정류장 ID")
    @ManyToOne
    private Station station;

    @JoinColumn(name = "bus_id", columnDefinition = "int")
    @ApiModelProperty(example = "버스 ID")
    @ManyToOne
    private Bus bus;

    @Column(name = "expected_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "도착 예정 시간/각?")
    private LocalDateTime expected;

    @Column(name = "created_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "생성일")
    private LocalDateTime created;
}
