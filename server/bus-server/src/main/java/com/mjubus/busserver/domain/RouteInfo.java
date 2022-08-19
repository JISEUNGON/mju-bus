package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="route_info")
@Getter
@Setter
public class RouteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    private String name;

    @ManyToOne
    @JoinColumn(name = "main_station_id", columnDefinition = "int")
    private Station station;

    @Column(name = "minute_required", columnDefinition = "int")
    private int minute;
}
