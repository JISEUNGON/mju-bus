package com.mjubus.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "게시물 저장 테이블")
@Table(name="post")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private int id;

    @Column(name="author", columnDefinition = "varchar(15)")
    private String author;

    @Column(name="title", columnDefinition = "text")
    private String title;

    @Column(name="content", columnDefinition = "longtext")
    private String content;

    @Column(name = "tag", columnDefinition = "text")
    private String tag;

    @Column(name="created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @Column(name="updated_at", columnDefinition = "datetime")
    private LocalDateTime updatedAt;
}
