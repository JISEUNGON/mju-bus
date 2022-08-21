package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="bus_timetable_info")
@Getter
@Setter
public class BusTimeTableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    private String name;
}
