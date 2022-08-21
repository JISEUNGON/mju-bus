package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@ApiModel(value = "버스 경로 테이블")
@Table(name="path_detail")
@Getter
@Setter
public class PathDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "path_info_id")
    @ApiModelProperty(example = "버스 경로 INFO 테이블 키")
    private PathInfo pathInfo;

    @Column(name = "latitude", columnDefinition = "decimal(9, 6)")
    @ApiModelProperty(example = "위도")
    private Double latitude;

    @Column(name = "longitude", columnDefinition = "decimal(9, 6)")
    @ApiModelProperty(example = "경도")
    private Double longitude;

    @Column(name = "path_order", columnDefinition = "int")
    @ApiModelProperty(example = "경로 순서")
    private int routeOrder;
}
