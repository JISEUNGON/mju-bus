package com.mjubus.server.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponse {

    private List<PostResponse> posts;

    public static PostListResponse of(List<PostResponse> postList) {
        return new PostListResponse(postList);
    }
}
