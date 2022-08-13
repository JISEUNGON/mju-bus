package com.mjubus.server.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@ApiModel(value = "정류장 테이블 ")
@Table(name="station")
@Getter
@Setter
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    @ApiModelProperty(example = "생활관(명현관)")
    private String name;

    @Column(name = "latitude", columnDefinition = "decimal(9, 6)")
    @ApiModelProperty(example = "위도")
    private Double latitude;

    @Column(name = "longitude", columnDefinition = "decimal(9, 6)")
    @ApiModelProperty(example = "경도")
    private Double longitude;

}
