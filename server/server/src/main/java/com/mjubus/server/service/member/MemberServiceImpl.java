package com.mjubus.server.service.member;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.MemberProvider;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.repository.MemberProviderRepository;
import com.mjubus.server.repository.MemberRepository;
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
}
