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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "sid", columnDefinition = "char(36)")
    private String sid;

    @Column(name = "name", columnDefinition = "char(36)")
    @ApiModelProperty(example = "버스 노선명")
    private String name;

    @Column(name = "charge", columnDefinition = "int")
    @ApiModelProperty(example = "버스 요금")
    private int charge;

    @Column(name = "type", columnDefinition = "tinyint")
    @ApiModelProperty(example = "버스 type")
    private int type;
}
