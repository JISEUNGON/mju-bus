package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@ApiModel(value = "버스 경로 메타 테이블")
@Table(name="path_info")
@Getter
@Setter
public class PathInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_from")
    @ApiModelProperty(example = "출발 정류장")
    private Station stationFrom;

    @ManyToOne
    @JoinColumn(name = "station_to")
    @ApiModelProperty(example = "도착 정류장")
    private Station stationAt;

}
