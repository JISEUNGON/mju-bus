package com.mjubus.server.service.member;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.MemberProvider;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.dto.request.JwtResponse;
import com.mjubus.server.exception.member.MemberNotFoundException;
import com.mjubus.server.exception.member.RefreshTokenInvalidException;
import com.mjubus.server.repository.MemberProviderRepository;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.service.taxiParty.TaxiPartyService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import com.mjubus.server.util.JwtUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberProviderRepository memberProviderRepository;

    private final TaxiPartyMembersService taxiPartyMembersService;
    private final TaxiPartyService taxiPartyService;

    public MemberServiceImpl(MemberRepository memberRepository, MemberProviderRepository memberProviderRepository, @Lazy TaxiPartyMembersService taxiPartyMembersService, TaxiPartyService taxiPartyService) {
        this.memberRepository = memberRepository;
        this.memberProviderRepository = memberProviderRepository;
        this.taxiPartyMembersService = taxiPartyMembersService;
        this.taxiPartyService = taxiPartyService;
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

    @Override
    public boolean hasGroupAuthority(Long id, String partyId) {
        Optional<List<TaxiPartyMembers>> partyMembersList = taxiPartyMembersService.findOptionalPartyMembersByPartyId(Long.parseLong(partyId));
        if (partyMembersList.isPresent()) {
            for (TaxiPartyMembers partyMembers : partyMembersList.get()) {
                if (partyMembers.getMember().getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isGroupAdminister(Long id, String partyId) {
        return taxiPartyService.findOptionalPartyById(Long.parseLong(partyId))
                .map(party -> party.getAdminister().getId().equals(id))
                .orElse(false);
    }
}
