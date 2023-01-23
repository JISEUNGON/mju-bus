package com.mjubus.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    USER("ROLE_MEMBER"), ADMIN("ROLE_ADMIN");

    private final String key;

}
