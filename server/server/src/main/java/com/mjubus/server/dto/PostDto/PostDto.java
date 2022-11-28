package com.mjubus.server.dto.PostDto;

import com.mjubus.server.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.html.HTMLDocument;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    /**
     * id           int
     * author       varchar(15)
     * title        text
     * content      longtext
     * tag          text
     * updated_at   datetime
     * created_at   datetime
     * */

    @ApiModelProperty(example = "지승언", dataType = "varchar(15)")
    private String author;

    @ApiModelProperty(example = "제목", dataType = "text")
    private String title;

    @ApiModelProperty(example = "본문 내용", dataType = "longtext")
    private String content;

    @ApiModelProperty(example = "태크들", dataType = "text")
    private String tag;

    @ApiModelProperty(example = "2021-06-06", dataType = "datetime")
    private LocalDateTime createdAt;

    @ApiModelProperty(example = "2022-11-01", dataType = "datetime")
    private LocalDateTime updatedAt;

    public PostDto(Post post) {
        author = post.getAuthor();
        title = post.getTitle();
        content = post.getContent();
        tag = post.getTag();
        createdAt = post.getCreatedAt();
        updatedAt = post.getUpdatedAt();
    }
}
