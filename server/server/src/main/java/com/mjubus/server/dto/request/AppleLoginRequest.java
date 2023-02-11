package com.mjubus.server.dto.request;


import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppleLoginRequest {
    private String authorizationCode;
    private String identityToken;
    private String user;

}
