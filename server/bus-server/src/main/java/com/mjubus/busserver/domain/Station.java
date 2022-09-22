package com.mjubus.busserver.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="station")
@Getter
@Setter
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    private String name;

    @Column(name = "latitude", columnDefinition = "double")
    private Double latitude;

    @Column(name = "longitude", columnDefinition = "double")
    private Double longitude;

    @Column(name = "kakao_latitude", columnDefinition = "double")
    private Double kakaoLatitude;

    @Column(name = "kakao_longitude", columnDefinition = "double")
    private Double kakaoLongitude;

}
