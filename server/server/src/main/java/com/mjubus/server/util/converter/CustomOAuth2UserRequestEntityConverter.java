package com.mjubus.server.util.converter;

import java.util.Collections;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class CustomOAuth2UserRequestEntityConverter  implements
    Converter<OAuth2UserRequest, RequestEntity<?>> {

    @Override
    public RequestEntity<?> convert(OAuth2UserRequest userRequest) {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        HttpMethod httpMethod = getHttpMethod(clientRegistration);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
            .fromUriString(clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri());

        RequestEntity<?> request;
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        headers.setBearerAuth(userRequest.getAccessToken().getTokenValue());
        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
        request = new RequestEntity<>(formParameters, headers, httpMethod, uriBuilder.build().toUri());


        return request;
    }

    private HttpMethod getHttpMethod(ClientRegistration clientRegistration) {
        HttpMethod httpMethod = HttpMethod.POST;
        if (clientRegistration.getProviderDetails().getUserInfoEndpoint().getAuthenticationMethod() != null) {
            httpMethod = HttpMethod.valueOf(
                clientRegistration.getProviderDetails().getUserInfoEndpoint().getAuthenticationMethod().getValue());
        }
        return httpMethod;
    }
}
