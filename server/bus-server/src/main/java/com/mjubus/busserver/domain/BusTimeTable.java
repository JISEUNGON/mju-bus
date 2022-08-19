package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="bus_timetable")
@Getter
@Setter
public class BusTimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "bus_calendar_id")
    private BusCalendar busCalendar;

    @ManyToOne
    @JoinColumn(name = "bus_timetable_info_id")
    private BusTimeTableInfo busTimeTableInfo;

}
