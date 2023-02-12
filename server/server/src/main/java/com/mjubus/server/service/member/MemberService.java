package com.mjubus.server.service.member;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.dto.request.JwtResponse;
import com.nimbusds.oauth2.sdk.TokenResponse;

public interface MemberService {

    /**
     * 회원가입 혹은 로그인
     *
     * @param appleAuthTokenDto Apple Refresh Token | Apple ID Token | Apple Access Token
     * @return Member
     */
    Member saveOrGetAppleMember(AppleAuthTokenDto appleAuthTokenDto);

    Member saveOrGetKakaoMember(KaKaoAuthTokenDto kaKaoAuthTokenDto);

    Member saveOrGetGoogleMember(GoogleAuthTokenDto googleAuthTokenDto);

    JwtResponse generateToken(Member member, String refreshToken);
    Member findMemberById(Long id);

    boolean hasGroupAuthority(Long id, String groupId);

    boolean isGroupAdminister(Long id, String groupId);
}