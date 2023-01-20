package com.mjubus.server.service.post;

import com.mjubus.server.dto.request.PostRequest;
import com.mjubus.server.dto.response.PostListResponse;
import com.mjubus.server.dto.response.PostResponse;
import com.mjubus.server.exception.post.PostNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class PostServiceTest {

    PostService postService;

    @Autowired
    public PostServiceTest(PostService postService) {
        this.postService = postService;
    }

    @Test
    @DisplayName("[Service][findPostByPostId] 정상적으로 게시글을 찾는다.")
    public void findPostById(){
        // 성공
        PostResponse success = postService.findPost(PostRequest.of(1));

        Assertions.assertThat(success).isNotNull();
    }

    @Test
    @DisplayName("[Service][findPostByPostId] 존재하지 않는 게시글을 찾는다.")
    public void findPostById2(){
        // 실패 - 예외처리
        assertThatThrownBy(() -> postService.findPost(PostRequest.of(2)))
            .isInstanceOf(PostNotFoundException.class);

    }

    @Test
    @DisplayName("[Service][findPostList] 존재하는 모든 게시글을 찾는다.")
    public void findPostList(){
        // 성공
        PostListResponse success = postService.findPostList();

        Assertions.assertThat(success).isNotNull();
    }
}
