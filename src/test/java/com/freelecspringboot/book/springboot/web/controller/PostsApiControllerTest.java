package com.freelecspringboot.book.springboot.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelecspringboot.book.springboot.domain.posts.Posts;
import com.freelecspringboot.book.springboot.domain.posts.PostsRepository;
import com.freelecspringboot.book.springboot.web.dto.PostsSaveRequestDto;
import com.freelecspringboot.book.springboot.web.dto.PostsUpdateRequestDto;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private MockMvc mvc;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    //인증된 "ROLE_USER" 생성
    @WithMockUser(roles="USER")
    public void Posts_등록() throws Exception {
        String title = "spring";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author").build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);
//        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정() throws Exception{
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
//        //when
//        ResponseEntity<Long> responseEntity =
//                restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        System.out.println(responseEntity);

        //when
        mvc.perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("boot");
        assertThat(all.get(0).getContent()).isEqualTo("content2");

    }

    @Test
    @WithMockUser(roles="USER")
    public void Post_삭제() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("spring")
                .content("content")
                .author("author").build());
        Posts savedPost = postsRepository.save(post);
        String url = "http://localhost:" + port + "/api/v1/posts/"+savedPost.getId();

//        restTemplate.exchange(url,HttpMethod.DELETE,null,Long.class);
//
//        assertThrows(RestClientException.class,()->restTemplate.exchange(url,HttpMethod.DELETE,null,Long.class));

        //when
        mvc.perform(MockMvcRequestBuilders.delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }
}