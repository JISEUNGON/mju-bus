package com.mjubus.server.util;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.member.MemberPrincipalDto;
import com.mjubus.server.enums.MemberRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public static String JWT_SECRET_KEY;
    private static final long EXPIRATION_TIME =  1000 * 60 * 60 * 24 * 3; // 3 days

    @Value("${external.jwt.secret}")
    public void setKey(String key) {
        JWT_SECRET_KEY = key;
    }

    public static String createJwt(Member member) {
        Date now = DateHandler.toDate(DateHandler.getToday());
        Date expiredDate = new Date(now.getTime() + EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public static boolean isExpired(String token) {
        Date expiredDate = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiredDate.before(new Date());
    }

    public static MemberPrincipalDto getMemberPrincipal(String token) {
        Map<String, Object> claims = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return MemberPrincipalDto.builder()
                .id((Long) claims.get("id"))
                .build();
    }

    public static boolean isKeyValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
