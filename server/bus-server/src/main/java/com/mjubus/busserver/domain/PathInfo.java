package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="path_info")
@Getter
@Setter
public class PathInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_from")
    private Station stationFrom;

    @ManyToOne
    @JoinColumn(name = "station_to")
    private Station stationAt;

}
