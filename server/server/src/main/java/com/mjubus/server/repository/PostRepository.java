package com.mjubus.server.repository;

import com.mjubus.server.domain.BusTimeTableInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<BusTimeTableInfo, Long> {
}
