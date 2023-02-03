package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@ApiModel(value = "택시 파티 정보")
@Table(name="taxi_party")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaxiParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private long id;

    @Column(name ="administer", columnDefinition = "int")
    @ApiModelProperty(example = "파티장")
    private long administer;

    @Column(name ="taxi_destination_id", columnDefinition = "int")
    @ApiModelProperty(example = "택시 도착 정류장")
    private long taxi_destination_id;

    @Column(name ="meeting_latitude", columnDefinition = "double")
    @ApiModelProperty(example = "만남장소_위도")
    private double meeting_latitude;

    @Column(name ="meeting_longitude", columnDefinition = "double")
    @ApiModelProperty(example = "만남장소_경도")
    private double meeting_longitude;

    @Column(name ="memo", columnDefinition = "char(30)")
    @ApiModelProperty(example = "장소관련 간단한 메모")
    private String memo;

    @Column(name ="min", columnDefinition = "int")
    @ApiModelProperty(example = "파티 최소 인원")
    private long min;

    @Column(name ="max", columnDefinition = "int")
    @ApiModelProperty(example = "파티 최대 인원")
    private long max;

    @Column(name ="end_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "모집 마감 시간")
    private long end_at;

    @Column(name ="created_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "생성일")
    private long created_at;

    @Column(name ="status", columnDefinition = "int")
    @ApiModelProperty(example = "모집 상황")
    private long status;
    /**
     * 1 : 모집중
     * 2 : 모집완료
     */
}
