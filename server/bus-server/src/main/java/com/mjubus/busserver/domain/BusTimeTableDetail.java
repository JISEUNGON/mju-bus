package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name="bus_timetable_detail")
@Getter
@Setter
public class BusTimeTableDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_timetable_info_id")
    private BusTimeTableInfo busTimeTableInfo;

    @Column(name = "depart_at", columnDefinition = "time")
    private LocalTime depart;
}