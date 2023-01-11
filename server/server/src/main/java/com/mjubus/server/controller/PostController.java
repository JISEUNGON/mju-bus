package com.mjubus.server.controller;

import com.mjubus.server.dto.PostDto.PostDto;
import com.mjubus.server.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@Api(tags = {"게시물 정보 확인 API"})
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    @ApiOperation(value = "게시물 정보를 검색한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 게시물이 없습니다.")
    })
    public PostDto info(@PathVariable(name = "postId") Integer id) {
        return postService.findPostById(id);
    }

    @GetMapping("/postList")
    @ApiOperation(value = "게시물 전체 출력")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "게시물이 없습니다.")
    })
    public List<PostDto> getAllPosts() {
        return postService.findPostList();
    }
}
