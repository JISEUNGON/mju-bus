package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Member;
import com.mjubus.server.util.JwtUtil;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(Member member) {
        return LoginResponse.builder()
                .accessToken(JwtUtil.createJwt(member))
                .refreshToken(member.getRefreshToken())
                .build();
    }
}
