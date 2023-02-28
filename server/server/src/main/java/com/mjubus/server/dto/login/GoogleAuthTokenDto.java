package com.mjubus.server.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GoogleAuthTokenDto {
    private String refreshToken;
    private LocalDateTime refreshTokenExpiresAt;

    private String fcmToken;
    @Setter
    private String userId;
}
