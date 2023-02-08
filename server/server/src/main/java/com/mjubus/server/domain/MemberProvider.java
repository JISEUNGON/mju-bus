package com.mjubus.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="member_provider")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MemberProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="provider", columnDefinition = "char(10)")
    private String provider;

    @Column(name="provider_id", columnDefinition = "char(255)")
    private String providerId;

    @Column(name="refresh_token", columnDefinition = "char(255)")
    private String refreshToken;

    @Column(name="refresh_token_expired_at", columnDefinition = "datetime")
    private LocalDateTime refreshTokenExpiredAt;
}
