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

    @Column(name = "latitude", columnDefinition = "decimal(9, 6)")
    private Double latitude;

    @Column(name = "longitude", columnDefinition = "decimal(9, 6)")
    private Double longitude;

}
