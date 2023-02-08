package com.mjubus.server.domain;

import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.util.DateHandler;
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

    public static MemberProvider of(KaKaoAuthTokenDto kaKaoAuthTokenDto) {
        return MemberProvider.builder()
                .provider("KAKAO")
                .providerId(kaKaoAuthTokenDto.getId())
                .refreshToken(kaKaoAuthTokenDto.getRefreshToken())
                .refreshTokenExpiredAt(kaKaoAuthTokenDto.getRefreshTokenExpiresAt())
                .build();
    }

    public static MemberProvider of(GoogleAuthTokenDto googleAuthTokenDto) {
        return MemberProvider.builder()
                .provider("GOOGLE")
                .providerId(googleAuthTokenDto.getUserId())
                .refreshToken(googleAuthTokenDto.getRefreshToken())
                .refreshTokenExpiredAt(DateHandler.getToday().plusYears(10))
                .build();
    }

    public static MemberProvider of(AppleAuthTokenDto appleAuthTokenDto) {
        return MemberProvider.builder()
                .provider("APPLE")
                .providerId(appleAuthTokenDto.getUser_id())
                .refreshToken(appleAuthTokenDto.getRefresh_token())
                .refreshTokenExpiredAt(DateHandler.getToday().plusYears(10))
                .build();
    }
}
