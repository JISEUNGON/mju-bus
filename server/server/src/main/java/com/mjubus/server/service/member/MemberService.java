package com.mjubus.server.service.member;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;

public interface MemberService {

    /**
     * 회원가입 혹은 로그인
     * @param appleAuthTokenDto Apple Refresh Token | Apple ID Token | Apple Access Token
     * @return Member
     */
    Member saveOrGetAppleMember(AppleAuthTokenDto appleAuthTokenDto);

    Member saveOrGetKakaoMember(KaKaoAuthTokenDto kaKaoAuthTokenDto);

    Member saveOrGetGoogleMember(GoogleAuthTokenDto googleAuthTokenDto);
}
