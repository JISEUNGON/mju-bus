package com.mjubus.server.dto.request;


import lombok.*;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginRequest {
    private String accessToken;
    private String accessTokenExpiresAt;
    private String refreshToken;
    private String refreshTokenExpiresAt;

}
