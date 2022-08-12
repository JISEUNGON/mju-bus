package com.mjubus.server.domain;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ApiModel(value = "정류장 테이블 ")
@Table(name="station")
@Getter
@Setter
public class Station {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "sid", columnDefinition = "char(36)")
    @ApiModelProperty(example = "458d6218-7ca1-49a5-9575-13da00d14550")
    private String sid;

    @Column(name = "name", columnDefinition = "char(36)")
    @ApiModelProperty(example = "생활관(명현관)")
    private String name;

    @Column(name = "latitude", columnDefinition = "decimal(9, 6)")
    @ApiModelProperty(example = "위도")
    private Double latitude;

    @Column(name = "longitude", columnDefinition = "decimal(9, 6)")
    @ApiModelProperty(example = "경도")
    private Double longitude;

    @Column(name = "type", columnDefinition = "int")
    @ApiModelProperty(example = "1")
    private int type;
}
