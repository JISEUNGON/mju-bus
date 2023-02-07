package com.mjubus.server.domain;

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

import lombok.*;
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

    @Enumerated(EnumType.ORDINAL)
    @Column(name="role", columnDefinition = "int")
    private MemberRole role;

    @Column(name="name", columnDefinition = "char(12)")
    private String name;

    @Column(name="profile", columnDefinition = "char(120)")
    private String profileImageUrl;

    @Column(name="refresh_token", columnDefinition = "char(120)")
    private String refreshToken;

    @Column(name="service", columnDefinition = "char(10)")
    private String serviceProvider;

    @Column(name="service_id", columnDefinition = "char(255)")
    private String serviceId;

    @Column(name="refresh_token_expired_at", columnDefinition = "datetime")
    private LocalDateTime serviceRefreshTokenExpiredAt;

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

}
