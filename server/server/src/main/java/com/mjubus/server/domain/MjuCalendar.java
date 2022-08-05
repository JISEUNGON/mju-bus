package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ApiModel(value = "학사 일정")
@Table(name="mju_calendar")
@Getter
@Setter
public class MjuCalendar {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "sid", columnDefinition = "char(36)")
    private String sid;

    @Column(name = "name", columnDefinition = "char(36)")
    @ApiModelProperty(example = "학사 일정명")
    private String name;

    @Column(name = "start", columnDefinition = "datetime")
    @ApiModelProperty(example = "학사 일정 시작일")
    private java.sql.Date start;

    @Column(name = "end", columnDefinition = "datetime")
    @ApiModelProperty(example = "학사 일정 종일")
    private java.sql.Date end;
}
