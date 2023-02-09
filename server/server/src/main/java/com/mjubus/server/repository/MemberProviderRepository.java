package com.mjubus.server.repository;

import com.mjubus.server.domain.MemberProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProviderRepository extends JpaRepository<MemberProvider, Long> {
    Optional<MemberProvider> findByProviderAndProviderId(String apple, String userId);
}
