package com.freelecspringboot.book.springboot.web.controller;

import com.freelecspringboot.book.springboot.domain.posts.Posts;
import com.freelecspringboot.book.springboot.domain.posts.PostsRepository;
import com.freelecspringboot.book.springboot.web.dto.PostsListResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void Index페이지(){
        String body = restTemplate.getForObject("/", String.class);
        assertThat(body).contains("스프링 부트로 시작하는 웹서비스");
    }
}