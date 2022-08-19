package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="bus_calendar")
@Getter
@Setter
public class BusCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    private String name;

    @Column(name = "start_at", columnDefinition = "datetime")
    private LocalDateTime start;

    @Column(name = "end_at", columnDefinition = "datetime")
    private LocalDateTime end;

    @Column(name = "weekend", columnDefinition = "BIT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean weekend;

    @Column(name = "priority", columnDefinition = "int")
    private int priority;
}
