package com.mjubus.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class PostListResponse {

    private List<PostResponse> posts;

    public static PostListResponse of(List<PostResponse> postList) {
        return new PostListResponse(postList);
    }
}
