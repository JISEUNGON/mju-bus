package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ApiModel(value = "버스 시간표")
@Table(name="bus_timetable")
@Getter
@Setter
public class BusTimeTable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "sid", columnDefinition = "char(36)")
    private String sid;

    @ManyToOne
    @JoinColumn(name = "bus_sid")
    @ApiModelProperty(example = "버스 SID")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "bus_calendar_sid")
    @ApiModelProperty(example = "명지대 학사일정 SID")
    private MjuCalendar mjuCalendar;

    @ManyToOne
    @JoinColumn(name = "bus_timetable_info_sid")
    @ApiModelProperty(example = "시간표 INFO SID")
    private BusTimeTableInfo busTimeTableInfo;

}
