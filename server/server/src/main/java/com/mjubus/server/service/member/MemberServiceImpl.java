package com.mjubus.server.service.member;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.MemberProvider;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.repository.MemberProviderRepository;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.util.DateHandler;
import com.mjubus.server.util.RefreshTokenGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberProviderRepository memberProviderRepository;

    public MemberServiceImpl(MemberRepository memberRepository, MemberProviderRepository memberProviderRepository) {
        this.memberRepository = memberRepository;
        this.memberProviderRepository = memberProviderRepository;
    }
    @Override
    public Member saveOrGetAppleMember(AppleAuthTokenDto appleAuthTokenDto) {
        Optional<MemberProvider> memberProviderOptional  = memberProviderRepository.findByProviderAndProviderId("APPLE", appleAuthTokenDto.getUser_id());
        if (memberProviderOptional.isPresent())
            return memberProviderOptional.get().getMember();
        else {

            Member member = Member.builder()
                    .name("APPLE 유저")
                    .profileImageUrl("sample_url")
                    .refreshToken(RefreshTokenGenerator.generateRefreshToken())
                    .refreshTokenExpiredAt(DateHandler.getToday().plusDays(14))
                    .role(MemberRole.GUEST)
                    .build();

            // Apple은 RefreshToken의 만료일이 없다.
            MemberProvider memberProvider = MemberProvider.builder()
                    .provider("APPLE")
                    .member(member)
                    .providerId(appleAuthTokenDto.getUser_id())
                    .refreshToken(appleAuthTokenDto.getRefresh_token())
                    .refreshTokenExpiredAt(DateHandler.getToday().plusYears(10))
                    .build();

            memberRepository.save(member);
            memberProviderRepository.save(memberProvider);

            return member;
        }
    }

    @Override
    public Member saveOrGetKakaoMember(KaKaoAuthTokenDto kaKaoAuthTokenDto) {
        Optional<MemberProvider> memberProviderOptional  = memberProviderRepository.findByProviderAndProviderId("KAKAO", kaKaoAuthTokenDto.getId());

        if (memberProviderOptional.isPresent())
            return memberProviderOptional.get().getMember();
        else {
            Member member = Member.builder()
                    .name("KAKAO 유저")
                    .profileImageUrl("sample_url")
                    .refreshToken(RefreshTokenGenerator.generateRefreshToken())
                    .refreshTokenExpiredAt(DateHandler.getToday().plusDays(14))
                    .role(MemberRole.GUEST)
                    .build();

            MemberProvider memberProvider = MemberProvider.builder()
                    .provider("APPLE")
                    .member(member)
                    .providerId(kaKaoAuthTokenDto.getId())
                    .refreshToken(kaKaoAuthTokenDto.getRefreshToken())
                    .refreshTokenExpiredAt(kaKaoAuthTokenDto.getRefreshTokenExpiresAt())
                    .build();

            memberRepository.save(member);
            memberProviderRepository.save(memberProvider);

            return member;
        }

    }

    @Override
    public Member saveOrGetGoogleMember(GoogleAuthTokenDto googleAuthTokenDto) {
        Optional<MemberProvider> memberProviderOptional  = memberProviderRepository.findByProviderAndProviderId("GOOGLE", googleAuthTokenDto.getUserId());

        if (memberProviderOptional.isPresent())
            return memberProviderOptional.get().getMember();
        else {
            Member member = Member.builder()
                    .name("GOOGLE 유저")
                    .profileImageUrl("sample_url")
                    .refreshToken(RefreshTokenGenerator.generateRefreshToken())
                    .refreshTokenExpiredAt(DateHandler.getToday().plusDays(14))
                    .role(MemberRole.GUEST)
                    .build();

            // Google은 RefreshToken의 만료일이 없다.
            MemberProvider memberProvider = MemberProvider.builder()
                    .provider("GOOGLE")
                    .member(member)
                    .providerId(googleAuthTokenDto.getUserId())
                    .refreshToken(googleAuthTokenDto.getRefreshToken())
                    .refreshTokenExpiredAt(DateHandler.getToday().plusYears(10))
                    .build();

            memberRepository.save(member);
            memberProviderRepository.save(memberProvider);

            return member;
        }
    }
}
