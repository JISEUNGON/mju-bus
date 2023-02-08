package com.mjubus.server.domain;

import com.mjubus.server.dto.login.KaKaoAuthTokenDto;
import com.mjubus.server.enums.MemberRole;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.mjubus.server.util.DateHandler;
import com.mjubus.server.util.RefreshTokenGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @Column(name="name", columnDefinition = "char(12)")
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="role", columnDefinition = "int")
    private MemberRole role;

    @Column(name="profile", columnDefinition = "char(120)")
    private String profileImageUrl;

    @Column(name="refresh_token", columnDefinition = "char(120)")
    private String refreshToken;

    @Column(name="refresh_token_expired_at", columnDefinition = "datetime")
    private LocalDateTime refreshTokenExpiredAt;

    @Column(name="created_at", columnDefinition = "datetime", updatable = false)
    private LocalDateTime createdAt;

    @ColumnDefault("1")
    @Column(name="status", columnDefinition = "bit")
    private Boolean status;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = true;
    }
    public void upgradeRoleFromGuestToUser() {
        this.role = MemberRole.USER;
    }

    public static Member of(String name) {
        return Member.builder()
                .name(name)
                .profileImageUrl("sample_url")
                .refreshToken(RefreshTokenGenerator.generateRefreshToken())
                .refreshTokenExpiredAt(DateHandler.getToday().plusYears(1))
                .role(MemberRole.GUEST)
                .build();
    }

}
