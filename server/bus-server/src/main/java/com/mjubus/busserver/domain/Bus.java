package com.mjubus.busserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="bus")
@Getter
@Setter
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    /**
     * `0` ~ `99` : 명지대학교 시내 셔틀버스
     * `100` ~ `199` :  명지대학교 시외 셔틀버스
     * `200` ~ `255` : 경기도 시외버스 (빨간버스)
     */
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    private String name;

    @Column(name = "charge", columnDefinition = "int")
    private Long charge;

}
