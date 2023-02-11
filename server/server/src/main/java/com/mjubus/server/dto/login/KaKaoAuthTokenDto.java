package com.mjubus.server.dto.login;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoAuthTokenDto {

    @Setter
    private String refreshToken;

    @Setter
    LocalDateTime refreshTokenExpiresAt;

    private String id;

    private String expires_in;

    private String app_id;
}
