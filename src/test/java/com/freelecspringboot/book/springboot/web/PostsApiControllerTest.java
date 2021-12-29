package com.freelecspringboot.book.springboot.web;

import com.freelecspringboot.book.springboot.domain.posts.Posts;
import com.freelecspringboot.book.springboot.domain.posts.PostsRepository;
import com.freelecspringboot.book.springboot.web.dto.PostsResponseDto;
import com.freelecspringboot.book.springboot.web.dto.PostsSaveRequestDto;
import com.freelecspringboot.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void Posts_등록(){
        String title = "spring";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author").build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정(){
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("spring")
                .content("content")
                .author("author").build());
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title("boot")
                .content("content2").build();
        String url = "http://localhost:" + port + "/api/v1/posts/"+savedPosts.getId();
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        //when
        ResponseEntity<Long> responseEntity =
                restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        System.out.println(responseEntity);
    }
}