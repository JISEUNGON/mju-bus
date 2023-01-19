package com.mjubus.server.service.post;

import com.mjubus.server.dto.request.PostRequest;
import com.mjubus.server.dto.response.PostListResponse;
import com.mjubus.server.dto.response.PostResponse;

public interface PostService {
    /**
     * Controller 에서 사용되는 메소드
     */
    PostResponse findPost(PostRequest req);
    PostListResponse findPostList();
}
