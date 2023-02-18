package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "station_location")
@ApiModel(value = "정류장(장소) 별 위도경도 테이블")
@Getter
@Setter
public class StationLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "priority", columnDefinition = "int")
    private Long priority;

    @Column(name = "name", columnDefinition = "varchar(15)")
    private String name;

    @Column(name = "latitude", columnDefinition = "double")
    private Double latitude;

    @Column(name = "longtitude", columnDefinition = "double")
    private Double longitude;

    @Column(name = "range", columnDefinition = "double")
    private Double range;
}
