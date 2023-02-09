package com.mjubus.server.service.member;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.MemberProvider;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.dto.request.JwtResponse;
import com.mjubus.server.exception.member.MemberNotFoundException;
import com.mjubus.server.exception.member.RefreshTokenInvalidException;
import com.mjubus.server.repository.MemberProviderRepository;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.util.JwtUtil;
import com.nimbusds.oauth2.sdk.TokenResponse;
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

            Member member = Member.of("APPLE 유저");
            MemberProvider memberProvider = MemberProvider.of(appleAuthTokenDto);
            memberProvider.setMember(member);

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
            Member member = Member.of("KAKAO 유저");
            MemberProvider memberProvider = MemberProvider.of(kaKaoAuthTokenDto);
            memberProvider.setMember(member);

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
            Member member = Member.of("GOOGLE 유저");
            MemberProvider memberProvider = MemberProvider.of(googleAuthTokenDto);
            memberProvider.setMember(member);

            memberRepository.save(member);
            memberProviderRepository.save(memberProvider);

            return member;
        }
    }

    @Override
    public JwtResponse generateToken(Member member, String refreshToken) {
        Member member_from_db = findMemberById(member.getId());

        if (member_from_db.getRefreshToken().equals(refreshToken)) {
            return JwtResponse.of(JwtUtil.createJwt(member_from_db));
        }

        throw new RefreshTokenInvalidException("Refresh Token is invalid");
    }

    @Override
    public Member findMemberById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        return memberOptional.orElseThrow(() -> new MemberNotFoundException("존재하지 않는 Member 입니다."));
    }
}
