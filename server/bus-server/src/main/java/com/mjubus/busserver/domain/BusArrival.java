package com.mjubus.busserver.domain;

import com.mjubus.busserver.util.DateHandler;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "bus_arrival")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusArrival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

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
