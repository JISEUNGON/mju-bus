package com.mjubus.server.dto.login;


import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppleLoginVo {
    private String authorizationCode;
    private String identityToken;
    private String user;

}
