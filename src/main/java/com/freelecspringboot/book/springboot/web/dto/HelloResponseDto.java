package com.freelecspringboot.book.springboot.web.dto;

import com.freelecspringboot.book.springboot.domain.posts.Posts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;

}
