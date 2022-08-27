package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@ApiModel(value = "경로 테이블")
@Table(name="route")
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    @ApiModelProperty(example = "버스 ID")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "route_info_id")
    @ApiModelProperty(example = "경로 ID")
    private RouteInfo routeInfo;


    @ManyToOne
    @JoinColumn(name = "bus_calendar_id")
    @ApiModelProperty(example = "명지대 학사일정 ID")
    private BusCalendar busCalendar;

}
