package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ApiModel(value = "경로 테이블")
@Table(name="route")
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "sid", columnDefinition = "char(36)")
    private String sid;

    @ManyToOne
    @JoinColumn(name = "bus_sid")
    @ApiModelProperty(example = "버스 SID")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "route_info_sid")
    @ApiModelProperty(example = "경로 SID")
    private RouteInfo routeInfo;


    @ManyToOne
    @JoinColumn(name = "bus_calendar_sid")
    @ApiModelProperty(example = "명지대 학사일정 SID")
    private BusCalendar busCalendar;
    
    @Column(name = "type", columnDefinition = "int")
    @ApiModelProperty(example = "20시 이후 시간표 결정 요소")
    // 1 : 20시 이전, 2: 20시 이후
    private int type;
}
