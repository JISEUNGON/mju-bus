package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Member;
import com.mjubus.server.enums.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private Long id;
    private String name;
    private String profileImageUrl;

    private MemberRole role;

    private boolean isActive;

    public static LoginResponse of(Member member) {
        return LoginResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .profileImageUrl(member.getProfileImageUrl())
                .role(member.getRole())
                .isActive(member.getStatus())
                .build();
    }
}
