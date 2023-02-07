package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@ApiModel(value = "택시 파티 정보")
@Table(name="taxi_destination")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaxiDestination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @Column(name ="name", columnDefinition = "char(36)")
    @ApiModelProperty(example = "택시 도착 정류장 이름")
    private String name;

    @Column(name ="latitude", columnDefinition = "double")
    @ApiModelProperty(example = "위도")
    private double latitude;

    @Column(name ="longitude", columnDefinition = "double")
    @ApiModelProperty(example = "경도")
    private double longitude;
}
