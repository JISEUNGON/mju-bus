package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Member;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.util.AccessTokenGenerator;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(Member member) {
        return LoginResponse.builder()
                .accessToken(AccessTokenGenerator.generateAccessToken(member))
                .refreshToken(member.getRefreshToken())
                .build();
    }
}
