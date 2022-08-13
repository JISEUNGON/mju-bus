package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ApiModel(value = "버스 정보")
@Table(name="bus")
@Getter
@Setter
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    /**
     * `0` ~ `99` : 명지대학교 시내 셔틀버스
     * `100` ~ `199` :  명지대학교 시외 셔틀버스
     * `200` ~ `255` : 경기도 시외버스 (빨간버스)
     */
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    @ApiModelProperty(example = "명지대역")
    private String name;

    @Column(name = "charge", columnDefinition = "int")
    @ApiModelProperty(example = "0", dataType = "int")
    private Long charge;

}
