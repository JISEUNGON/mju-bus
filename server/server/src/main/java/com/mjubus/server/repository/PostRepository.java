package com.mjubus.server.repository;

import com.mjubus.server.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findPostById(@Param(value = "id") Integer id);

    List<Post> findAll();
}
