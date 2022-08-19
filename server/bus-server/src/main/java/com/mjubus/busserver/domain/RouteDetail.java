package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="route_detail")
@Getter
@Setter
public class RouteDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_info_id", columnDefinition = "int")
    private RouteInfo routeInfo;

    @ManyToOne
    @JoinColumn(name = "station_id", columnDefinition = "int")
    private Station station;

    @Column(name = "route_order", columnDefinition = "int")
    private int order;

}
