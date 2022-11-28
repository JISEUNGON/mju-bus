package com.mjubus.server.repository;

import com.mjubus.server.domain.Post;
import com.mjubus.server.dto.PostDto.PostDto;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public Optional<Post> findPostById(@Param(value = "id") Integer id);

    public List<Post> findAll();
}
