package com.mjubus.server.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String accessToken;


    public static JwtResponse of(String refreshToken) {
        return JwtResponse.builder()
                .accessToken(refreshToken)
                .build();
    }
}
