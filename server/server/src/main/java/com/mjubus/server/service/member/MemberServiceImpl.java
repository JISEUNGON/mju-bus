package com.mjubus.server.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.MemberProvider;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.login.AppleAuthTokenDto;
import com.mjubus.server.dto.login.GoogleAuthTokenDto;
import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.dto.member.MemberPrincipalDto;
import com.mjubus.server.dto.request.JwtResponse;
import com.mjubus.server.dto.request.MjuAuthInfoRequest;
import com.mjubus.server.dto.response.MemberResponse;
import com.mjubus.server.dto.response.MjuAuthInfoResponse;
import com.mjubus.server.exception.auth.MjuUserNotFoundException;
import com.mjubus.server.exception.member.MemberNotFoundException;
import com.mjubus.server.exception.member.RefreshTokenInvalidException;
import com.mjubus.server.repository.MemberProviderRepository;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.service.taxiParty.TaxiPartyService;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import com.mjubus.server.util.JwtUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberProviderRepository memberProviderRepository;

    private final TaxiPartyMembersService taxiPartyMembersService;
    private final TaxiPartyService taxiPartyService;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MemberServiceImpl(MemberRepository memberRepository, MemberProviderRepository memberProviderRepository, @Lazy TaxiPartyMembersService taxiPartyMembersService, TaxiPartyService taxiPartyService, RestTemplate restTemplate) {
        this.memberRepository = memberRepository;
        this.memberProviderRepository = memberProviderRepository;
        this.taxiPartyMembersService = taxiPartyMembersService;
        this.taxiPartyService = taxiPartyService;
        this.restTemplate = restTemplate;
    }
    @Override
    public Member saveOrGetAppleMember(AppleAuthTokenDto appleAuthTokenDto) {
        Optional<MemberProvider> memberProviderOptional  = memberProviderRepository.findByProviderAndProviderId("APPLE", appleAuthTokenDto.getUser_id());
        if (memberProviderOptional.isPresent())
            return memberProviderOptional.get().getMember();
        else {

            Member member = Member.of("APPLE 유저");
            member.setFcmToken(appleAuthTokenDto.getFcm_token());

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
            member.setFcmToken(kaKaoAuthTokenDto.getFcmToken());

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
            member.setFcmToken(googleAuthTokenDto.getFcmToken());

            MemberProvider memberProvider = MemberProvider.of(googleAuthTokenDto);
            memberProvider.setMember(member);

            memberRepository.save(member);
            memberProviderRepository.save(memberProvider);

            return member;
        }
    }

    @Override
    public JwtResponse generateToken(MemberPrincipalDto principal, String refreshToken) {
        Member member = findMemberById(principal.getId());

        if (member.getRefreshToken().equals(refreshToken)) {
            return JwtResponse.of(JwtUtil.createJwt(member));
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

    @Override
    @Transactional
    public MemberResponse authMjuStudent(MemberPrincipalDto principal, MjuAuthInfoRequest request) {
        Member member = findMemberById(principal.getId());

        // 명지대 인증 정보 조회
        if (isMjuStudent(request)) {
            member.upgradeRoleFromGuestToUser();
        }

        memberRepository.save(member);
        return MemberResponse.of(member);
    }

    @Override
    public MemberResponse findMemberByMemberPrincipal(MemberPrincipalDto principal) {
        Member member = findMemberById(principal.getId());
        return MemberResponse.of(member);
    }

    @Override
    public Optional<Member> findOptionalMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId);
    }


    public Boolean isMjuStudent(MjuAuthInfoRequest request) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("nm", request.getName());
        map.add("birthday", request.getBirthday());

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        headers.add("Referer", "https://sso1.mju.ac.kr/login.do");
        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://sso1.mju.ac.kr/mju/findId.do", httpRequest, String.class);

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("error").toString().replace("\"", "").equals("0000");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
