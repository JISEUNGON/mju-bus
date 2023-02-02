package com.mjubus.server.repository;

import com.mjubus.server.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
}
