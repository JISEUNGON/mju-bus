package com.mjubus.server.service.oauth;

import com.mjubus.server.domain.Member;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.util.DateHandler;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

public class OAuth2LoginFactory {

    public static Member ofKakao(OAuth2UserRequest userRequest, Map<String, Object> response) {
        return Member.builder()
                .role(MemberRole.USER)
                .name("kakao_tester")
                .profileImageUrl("null")
                .refreshToken((String) userRequest.getAdditionalParameters().get("refresh_token"))
                .serviceRefreshTokenExpiredAt(DateHandler.getToday().plusSeconds((Integer) userRequest.getAdditionalParameters().get("refresh_token_expires_in")))
                .serviceProvider("KAKAO")
                .serviceId((Long) response.get("id"))
                .build();
    }

}
