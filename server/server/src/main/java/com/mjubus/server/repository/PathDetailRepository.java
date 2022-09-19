package com.mjubus.server.repository;

import com.mjubus.server.domain.PathDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PathDetailRepository extends JpaRepository<PathDetail, Long> {

    Optional<List<PathDetail>> findPathDetailsByPathInfo_IdOrderByRouteOrder(Long pathInfo_id);
}