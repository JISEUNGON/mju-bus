package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="path_detail")
@Getter
@Setter
public class PathDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "path_info_id")
    private PathInfo pathInfo;

    @Column(name = "latitude", columnDefinition = "decimal(9, 6)")
    private Double latitude;

    @Column(name = "longitude", columnDefinition = "decimal(9, 6)")
    private Double longitude;

    @Column(name = "path_order", columnDefinition = "int")
    private int routeOrder;
}
