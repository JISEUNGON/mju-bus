package com.mjubus.server.service.post;

import com.mjubus.server.domain.Post;
import com.mjubus.server.dto.request.PostRequest;
import com.mjubus.server.dto.response.PostListResponse;
import com.mjubus.server.dto.response.PostResponse;
import com.mjubus.server.exception.post.PostNotFoundException;
import com.mjubus.server.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostResponse findPost(PostRequest req) {
        Post result = postRepository.findPostById(req.getId()).orElseThrow(() -> new PostNotFoundException(req.getId()));
        return PostResponse.of(result);
    }

    public PostListResponse findPostList() {
        List<Post> result = postRepository.findAll();
        List<PostResponse> postList = new ArrayList<>();
        for (Post post : result) {
            postList.add(PostResponse.of(post));
        }
        return PostListResponse.of(postList);
    }
}
