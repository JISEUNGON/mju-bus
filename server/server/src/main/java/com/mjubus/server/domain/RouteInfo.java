package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@ApiModel(value = "노선도 Info 테이블 ")
@Table(name="route_info")
@Getter
@Setter
public class RouteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    @ApiModelProperty(example = "노선명")
    private String name;

    @ManyToOne
    @JoinColumn(name = "main_station_id", columnDefinition = "int")
    @ApiModelProperty(example = "main_station_id")
    private Station station;

    @Column(name = "minute_required", columnDefinition = "int")
    @ApiModelProperty(example = "main_station_sid 까지 평균 소요 시간")
    private int minute;
}
