package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="route")
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "route_info_id")
    private RouteInfo routeInfo;


    @ManyToOne
    @JoinColumn(name = "bus_calendar_id")
    private BusCalendar busCalendar;
    
    /*@Column(name = "type", columnDefinition = "int")
    // 1 : 20시 이전, 2: 20시 이후
    private int type;*/
}
