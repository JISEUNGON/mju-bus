package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="path_navi_detail")
@Getter
@Setter
public class PathNaviDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "path_info_id")
    private PathInfo pathInfo;

    @ManyToOne
    @JoinColumn(name = "station_from")
    private Station stationFrom;

    @ManyToOne
    @JoinColumn(name = "station_to")
    private Station stationAt;

    @Column(name = "navi_order")
    private int naviOrder;

}
