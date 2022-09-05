package com.mjubus.busserver.domain;

import com.mjubus.busserver.util.DateHandler;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="bus_arrival")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusArrival {
    @Id
    @Column(name ="sid", columnDefinition = "char(36)")
    private String sid;

    @Column(name = "pre_bus_arrival_sid", columnDefinition = "char(36)")
    private String preSid;

    @JoinColumn(name = "station_id", columnDefinition = "int")
    @ManyToOne
    private Station station;

    @JoinColumn(name = "bus_id", columnDefinition = "int")
    @ManyToOne
    private Bus bus;

    @Column(name = "expected_at", columnDefinition = "datetime")
    private LocalDateTime expected;

    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        created = DateHandler.getToday();
    }
}
