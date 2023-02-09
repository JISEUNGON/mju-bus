package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Long id;

    @OneToOne
    @JoinColumn(name = "administer")
    @ApiModelProperty(example = "파티장")
    private Member administer;

    @OneToOne
    @JoinColumn(name = "taxi_destination_id")
    @ApiModelProperty(example = "택시 도착 정류장")
    private TaxiDestination taxi_destination_id;

    @Column(name ="meeting_latitude", columnDefinition = "double")
    @ApiModelProperty(example = "만남장소_위도")
    private Double meeting_latitude;

    @Column(name ="meeting_longitude", columnDefinition = "double")
    @ApiModelProperty(example = "만남장소_경도")
    private Double meeting_longitude;

    @Column(name = "meeting_place", columnDefinition = "char(50)")
    @ApiModelProperty(example = "만남장소")
    private String meeting_place;

    @Column(name ="memo", columnDefinition = "char(30)")
    @ApiModelProperty(example = "장소관련 간단한 메모")
    private String memo;

    @Column(name ="min", columnDefinition = "int")
    @ApiModelProperty(example = "파티 최소 인원")
    private Long min;

    @Column(name ="max", columnDefinition = "int")
    @ApiModelProperty(example = "파티 최대 인원")
    private Long max;

    @Column(name ="end_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "모집 마감 시간")
    private LocalDateTime end_at;

    @Column(name ="created_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "생성일")
    private LocalDateTime created_at;

    @Column(name ="status", columnDefinition = "int")
    @ApiModelProperty(example = "모집 상황")
    private Long status;
    /**
     * 1 : 모집중
     * 2 : 모집완료
     */
}
