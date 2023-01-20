package com.mjubus.server.controller;

import com.mjubus.server.dto.request.PostRequest;
import com.mjubus.server.dto.response.PostListResponse;
import com.mjubus.server.dto.response.PostResponse;
import com.mjubus.server.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@Api(tags = {"게시물 정보 확인 API"})
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "게시물 정보를 검색한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 게시물이 없습니다.")
    })
    public ResponseEntity<PostResponse> info(@PathVariable(name = "postId") PostRequest id) {
        return ResponseEntity.ok(
                postService.findPost(id)
        );
    }

    @GetMapping("/postList")
    @ApiOperation(value = "게시물 전체 출력")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    public ResponseEntity<PostListResponse> getAllPosts() {
        return ResponseEntity.ok(postService.findPostList());
    }
}
