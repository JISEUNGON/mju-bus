package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSimpleResponse {

    private String profile;
    private String name;

    public static MemberSimpleResponse of(Member member) {
        return MemberSimpleResponse.builder()
                .profile(member.getProfileImageUrl())
                .name(member.getName())
                .build();
    }
}
