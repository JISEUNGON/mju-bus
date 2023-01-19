package com.mjubus.server.dto.response;

import com.mjubus.server.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@ToString
@Builder
@Getter
public class PostResponse {

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

    public static PostResponse of(String author, String title, String content, String tag, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return PostResponse.builder()
                .author(author)
                .title(title)
                .content(content)
                .tag(tag)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static PostResponse of(Post result) {
        return PostResponse.builder()
                .author(result.getAuthor())
                .title(result.getTitle())
                .content(result.getContent())
                .tag(result.getTag())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();
    }
}
