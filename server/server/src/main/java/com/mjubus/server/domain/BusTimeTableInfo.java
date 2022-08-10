package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ApiModel(value = "버스 시간표 메타 테이블")
@Table(name="bus_timetable_info")
@Getter
@Setter
public class BusTimeTableInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "sid", columnDefinition = "char(36)")
    private String sid;

    @ApiModelProperty(example = "버스 시간표 명")
    @Column(name = "name", columnDefinition = "char(36)")
    private String name;
}
