package com.freelecspringboot.book.springboot.service.posts;

import com.freelecspringboot.book.springboot.domain.posts.Posts;
import com.freelecspringboot.book.springboot.domain.posts.PostsRepository;
import com.freelecspringboot.book.springboot.web.dto.PostsListResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostsServiceTest {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    PostsService postsService;

    @Test
    public void DateFormat_test(){
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("spring")
                .content("content")
                .author("author").build());

        List<PostsListResponseDto> list = postsService.findAllDesc();
        System.out.println(list.get(0).getModifiedDate());
    }
}