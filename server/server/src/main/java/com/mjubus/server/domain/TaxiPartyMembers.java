package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@ApiModel(value = "택시 파티 멤버 정보")
@Table(name="taxi_party_members")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaxiPartyMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @ApiModelProperty(example = "파티 멤버")
    private Station member_id;

    @ManyToOne
    @JoinColumn(name = "taxi_party_id")
    @ApiModelProperty(example = "파티 ID")
    private Station taxi_party_id;
}
