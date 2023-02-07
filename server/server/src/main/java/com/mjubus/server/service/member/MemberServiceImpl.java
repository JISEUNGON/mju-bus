package com.mjubus.server.service.member;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.util.DateHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Override
    public Member saveOrGetAppleMember(AppleAuthTokenDto appleAuthTokenDto) {
        Optional<Member> memberOptional  = memberRepository.findMemberByServiceProviderAndServiceId("APPLE", appleAuthTokenDto.getId_token());
        if (memberOptional.isPresent())
            return memberOptional.get();
        else {
            // Apple은 RefreshToken이 유저가 수동적으로 발생하는 이벤트 외에는 만료되지 않음.
            Member member = Member.builder()
                    .serviceProvider("APPLE")
                    .name("APPLE 유저")
                    .serviceId(appleAuthTokenDto.getUser_id())
                    .refreshToken(appleAuthTokenDto.getRefresh_token())
                    .serviceRefreshTokenExpiredAt(DateHandler.getToday().plusYears(100))
                    .role(MemberRole.GUEST)
                    .build();
            return memberRepository.save(member);
        }
    }

    @Override
    public Member saveOrGetKakaoMember(KaKaoAuthTokenDto kaKaoAuthTokenDto) {
        Optional<Member> memberOptional = memberRepository.findMemberByServiceProviderAndServiceId("KAKAO", kaKaoAuthTokenDto.getId());
        if (memberOptional.isPresent())
            return memberOptional.get();
        else {
            Member member = Member.builder()
                    .serviceProvider("KAKAO")
                    .name("KAKAO 유저")
                    .serviceId(kaKaoAuthTokenDto.getId())
                    .refreshToken(kaKaoAuthTokenDto.getRefreshToken())
                    .serviceRefreshTokenExpiredAt(kaKaoAuthTokenDto.getRefreshTokenExpiresAt())
                    .role(MemberRole.GUEST)
                    .build();
            return memberRepository.save(member);
        }

    }

    @Override
    public Member saveOrGetGoogleMember(GoogleAuthTokenDto googleAuthTokenDto) {
        Optional<Member> memberOptional = memberRepository.findMemberByServiceProviderAndServiceId("GOOGLE", googleAuthTokenDto.getUserId());
        if (memberOptional.isPresent())
            return memberOptional.get();
        else {
            Member member = Member.builder()
                    .serviceProvider("GOOGLE")
                    .name("GOOGLE 유저")
                    .serviceId(googleAuthTokenDto.getUserId())
                    .refreshToken(googleAuthTokenDto.getRefreshToken())
                    .serviceRefreshTokenExpiredAt(googleAuthTokenDto.getRefreshTokenExpiresAt())
                    .role(MemberRole.GUEST)
                    .build();
            return memberRepository.save(member);
        }
    }
}

