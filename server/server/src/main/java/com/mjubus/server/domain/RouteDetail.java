package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ApiModel(value = "노선도 Detail 테이블 ")
@Table(name="route_detail")
@Getter
@Setter
public class RouteDetail {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "sid", columnDefinition = "char(36)")
    private String sid;

    @ManyToOne
    @JoinColumn(name = "route_info_sid", columnDefinition = "char(36)")
    @ApiModelProperty(example = "route_info_sid")
    private RouteInfo routeInfo;

    @ManyToOne
    @JoinColumn(name = "station_sid", columnDefinition = "char(36)")
    @ApiModelProperty(example = "정류장 SID")
    private Station station;

    @Column(name = "route_order", columnDefinition = "int")
    private int order;

}
