package com.mjubus.server.dto.response;

import com.mjubus.server.enums.MemberRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberResponse {

    @ApiModelProperty(example = "고유 식별 ID")
    private Long id;

//    @ApiModelProperty(example = "멤버 역할", dataType = "int")
//    private MemberRole role;

    @ApiModelProperty(example = "이름", dataType = "char(36)")
    private String name;

    @ApiModelProperty(example = "프로필 URL", dataType = "char(120)")
    private String profileImageUrl;

//    @ApiModelProperty(example = "Refresh Token", dataType = "char(120)")
//    private String refreshToken;
//
//    @ApiModelProperty(example = "서비스 종류", dataType = "char(10)")
//    private String serviceProvider;
//
//    @ApiModelProperty(example = "서비스 Id", dataType = "bigint")
//    private Long serviceId;
//
//    @ApiModelProperty(example = "서비스 RefreshToken 만료일", dataType = "datetime")
//    private LocalDateTime serviceRefreshTokenExpiredAt;
//
//    @ApiModelProperty(example = "가입일", dataType = "datetime")
//    private LocalDateTime createdAt;
//
//    @ApiModelProperty(example = "상태", dataType = "bit")
//    private Boolean status;

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
}
