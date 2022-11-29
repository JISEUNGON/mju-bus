package com.mjubus.server.service.post;

import com.mjubus.server.domain.Post;
import com.mjubus.server.dto.PostDto.PostDto;
import com.mjubus.server.exception.post.PostNotFoundException;
import com.mjubus.server.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PostDto findPostById(Integer id) {
        Post result = postRepository.findPostById(id).orElseThrow(() -> new PostNotFoundException(id));
        return new PostDto(result);
    }

    public List<PostDto> findPostList() {
        List<Post> all = postRepository.findAll();
        List<PostDto> posts = new ArrayList<PostDto>();

        for(Post i : all) {
            posts.add(new PostDto(i));
        }

        return posts;
    }
}
