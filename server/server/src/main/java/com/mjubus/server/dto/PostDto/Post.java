package com.mjubus.server.dto.PostDto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.swing.text.html.HTMLDocument;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="post")
@Getter
@Setter
public class Post {
    /**
     * id           int
     * author       varchar(15)
     * title        text
     * content      longtext
     * tag          text
     * updated_at   datetime
     * created_at   datetime
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", columnDefinition = "int")
    private Long id;

    @Column(name = "author", columnDefinition = "varchar(15)")
    private String author;

    @Column(name = "title", columnDefinition = "text")
    private HTMLDocument description;

    @Column(name = "content", columnDefinition = "longtext")
    private HTMLDocument content;

    @Column(name = "tag", columnDefinition = "text")
    private LocalDate tag;

    @Column(name = "updated_at", columnDefinition = "datetime")
    private LocalTime updatedAt;

    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalTime createdAt;
}
