package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@ApiModel(value = "버스 시간표 DETAIL")
@Table(name="bus_timetable_detail")
@Getter
@Setter
public class BusTimeTableDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_timetable_info_id")
    @ApiModelProperty(example = "버스 시간표 ID")
    private BusTimeTableInfo busTimeTableInfo;

    @Column(name = "depart_at", columnDefinition = "time")
    @ApiModelProperty(example = "출발 시간")
    private Date depart;
}
