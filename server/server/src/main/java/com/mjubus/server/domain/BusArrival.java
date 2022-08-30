package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "버스 도착 정보")
@Table(name="bus_arrival")
@Getter
@Setter
@NamedNativeQuery(
        name = "bus_arrival_list",
        query ="SELECT id, station_id as stationId, bus_id as busId, expected_at as expectedAt, max(created_at) as createdAt" +
        "       FROM bus_arrival" +
        "       WHERE station_id = :stationId" +
        "       GROUP BY bus_id",
        resultSetMapping = "busArrivalDto"
)
@SqlResultSetMapping(
        name = "busArrivalDto", classes = @ConstructorResult(
        targetClass = BusArrival.class,
        columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "station_Id", type = Long.class),
                @ColumnResult(name = "bus_id", type = Long.class),
                @ColumnResult(name = "expected_at", type = LocalDateTime.class),
                @ColumnResult(name = "created", type = LocalDateTime.class)
        }
))
public class BusArrival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @JoinColumn(name = "station_id", columnDefinition = "int")
    @ApiModelProperty(example = "정류장 ID")
    @ManyToOne
    private Station station;

    @JoinColumn(name = "bus_id", columnDefinition = "int")
    @ApiModelProperty(example = "버스 ID")
    @ManyToOne
    private Bus bus;

    @Column(name = "expected_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "도착 예정 시간/각?")
    private LocalDateTime expected;

    @Column(name = "created_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "생성일")
    private LocalDateTime created;

}
