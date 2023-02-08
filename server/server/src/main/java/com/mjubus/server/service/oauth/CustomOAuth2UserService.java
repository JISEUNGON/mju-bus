package com.mjubus.server.service.oauth;

import com.mjubus.server.domain.Member;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.util.converter.CustomOAuth2UserRequestEntityConverter;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        RequestEntity<?> requestEntity = new CustomOAuth2UserRequestEntityConverter().convert(userRequest);
        ResponseEntity<Map> responseEntity = getResponse(requestEntity);
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Member member = null;

        // Service (네이버, 카카오, Apple)
        String service = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        String refreshToken = (String) userRequest.getAdditionalParameters().get(
            OAuth2ParameterNames.REFRESH_TOKEN);
        Integer refreshTokenExpiresIn = (Integer) userRequest.getAdditionalParameters().get("refresh_token_expires_in");
        switch (service) {
            case "NAVER":
//                member = OAuth2LoginFactory.ofNaver(refreshToken, refreshTokenExpiresIn);
                break;
            case "KAKAO":
                member = OAuth2LoginFactory.ofKakao(userRequest, responseEntity.getBody());
                break;
            case "APPLE":
//                member = OAuth2LoginFactory.ofApple(refreshToken, refreshTokenExpiresIn);
                break;
        }

        if (memberRepository.findMemberByServiceProviderAndServiceId(member.getServiceProvider(), member.getServiceId()).isEmpty()) {
            memberRepository.save(member);
        } else {
            // todo : 가능성 검토 및 대응 로직
            throw new RuntimeException("이미 가입된 회원입니다.");
        }

        httpSession.setAttribute("member", member);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName();

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
            oAuth2User.getAttributes(), userNameAttributeName);
    }

    /**
     * RestTemplate을 사용하여 RequestEntity를 보내고, ResponseEntity를 받아온다.
     * @param request RequestEntity (유저 정보를 받아오는 Entity)
     * @return ResponseEntity (유저 정보를 담은 Entity)
     */
    private ResponseEntity<Map> getResponse(RequestEntity<?> request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        restTemplate.setRequestFactory(factory);
        ResponseEntity<Map> response = restTemplate.exchange(request, Map.class);

        return response;
    }
}
