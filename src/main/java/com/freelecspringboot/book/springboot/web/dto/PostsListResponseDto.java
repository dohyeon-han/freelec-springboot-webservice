package com.freelecspringboot.book.springboot.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.freelecspringboot.book.springboot.domain.posts.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String  title;
    private String author;
    private String modifiedDate;

    public PostsListResponseDto(Posts posts){
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.author = posts.getAuthor();
        this.modifiedDate = posts.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
