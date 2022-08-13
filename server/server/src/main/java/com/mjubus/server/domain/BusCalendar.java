package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@ApiModel(value = "학사 일정")
@Table(name="bus_calendar")
@Getter
@Setter
public class BusCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @Column(name = "name", columnDefinition = "char(36)")
    @ApiModelProperty(example = "학사 일정명")
    private String name;

    @Column(name = "start_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "학사 일정 시작일")
    private LocalDateTime start;

    @Column(name = "end_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "학사 일정 종일")
    private LocalDateTime end;

    @Column(name = "weekend", columnDefinition = "BIT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @ApiModelProperty(example = "주말 여부")
    private boolean weekend;

    @Column(name = "priority", columnDefinition = "int")
    @ApiModelProperty(example = "일정 우선순위")
    private int priority;
}
