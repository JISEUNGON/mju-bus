package com.mjubus.server.service.login;

import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.service.member.MemberService;
import com.mjubus.server.service.member.MemberServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private static LoginStrategy loginStrategy;

    private static MemberRepository memberRepository;
    @BeforeAll
    public static void setUp() {
        MemberService memberService = new MemberServiceImpl(memberRepository);
        loginStrategy = new AppleLoginStrategy(memberService);
    }
    @Test
    @Transactional
    @DisplayName("[로그인] 애플 로그인 테스트")
     void appleLogin() {
        String text = "eyJhdXRob3JpemF0aW9uQ29kZSI6ImM0Yzk3NjNhZWY4YTg0OTkxOTNlMDQ5ZTkwZDY4NDhlZC4wLnN6cnguY08zSC1JVUFRWXJOazVKU1Z5TEktZyIsImlkZW50aXR5VG9rZW4iOiJleUpyYVdRaU9pSm1hRFpDY3poRElpd2lZV3huSWpvaVVsTXlOVFlpZlEuZXlKcGMzTWlPaUpvZEhSd2N6b3ZMMkZ3Y0d4bGFXUXVZWEJ3YkdVdVkyOXRJaXdpWVhWa0lqb2lZMjl0TG0xcWRXSjFjeTV0WW5Weklpd2laWGh3SWpveE5qYzFPRFV6TURRMkxDSnBZWFFpT2pFMk56VTNOalkyTkRZc0luTjFZaUk2SWpBd01Ea3hOeTQ0WVRsbE0ySXdPV1kzT1RNME5UVXhPRGd6WXprd1lUSmtaalpoT1dJMlppNHdPRE0zSWl3aWJtOXVZMlVpT2lKaE9USTROREpoTURJeE5tSTJOemMzTXpZMFl6azVZV0ptWkdZelpEY3hZbU01TW1FeE1UVmtOalUwWTJWaU5tUTFZelppTWpjMVlUa3dabVU1WW1abElpd2lZMTlvWVhOb0lqb2lWVXhXYWtNNVlXMDRUaTFTWjNkVU5VUTVOME5TWnlJc0ltRjFkR2hmZEdsdFpTSTZNVFkzTlRjMk5qWTBOaXdpYm05dVkyVmZjM1Z3Y0c5eWRHVmtJanAwY25WbGZRLmRQTDVkNkhETTk3Rmh1YmpMb1RnWTVBX1pLajZKUTJtYUZRNDI0aDlreFpLYkhSbURFRUhVUVZhUXlmOGIwVDBxZTRicVpDemNUUlNGTGVhc2F1UXNqUHRyQWRvbWk3U2dodzhPcmVrZEhLUjM4cWMyWFdhZlctV2d1WGZ4Tmg0NExOTDVEc3E3aWNMelBJdmY1RWl5S0RoUXlfajI1V0VZRE9lTTJZbUl6RGh6ZnlYV01TSGJKUHJ0dGJIOVUtZ1c0MnVncDVsLUV3RlQxY2tFN1dnUjI5YWpvZDVyZ3hiV1F2cHpTZjFWdS1iaDNXN0lDdXIxMk9NU3VPOEV6UVJLSFdmeE1kVU9VcVgyU1B5U0RKZmprRUdHaGVnMkJ5ZFhodjlBLWlEd1FoV0NkMk9YYXNpXzB1ZXNjN1BEb1k0ZmRNdUViZnVCeFZYVG50LVhyMU5nUSIsInVzZXIiOiIwMDA5MTcuOGE5ZTNiMDlmNzkzNDU1MTg4M2M5MGEyZGY2YTliNmYuMDgzNyJ9";

        LoginResponse response = loginStrategy.login(text);

        assertNotNull(response);
        assertNotNull(response.getId());
    }
}