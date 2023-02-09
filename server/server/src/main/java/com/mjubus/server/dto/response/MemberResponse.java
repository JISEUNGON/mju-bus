package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Member;
import com.mjubus.server.enums.MemberRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class MemberResponse {

    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

    @ApiModelProperty(example = "이름", dataType = "char(36)")
    private String name;

    @ApiModelProperty(example = "프로필 URL", dataType = "char(120)")
    private String profileImageUrl;

    @ApiModelProperty(example = "멤버 권한")
    private MemberRole role;

    public static MemberResponse of (Long id, String name, String profileImageUrl){
        return MemberResponse.builder()
                .id(id)
                .name(name)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public static MemberResponse of (MemberResponse result){
        return MemberResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .profileImageUrl(result.getProfileImageUrl())
                .build();
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .profileImageUrl(member.getProfileImageUrl())
                .role(member.getRole())
                .build();
    }
}
