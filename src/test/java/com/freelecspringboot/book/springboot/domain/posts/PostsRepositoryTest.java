package com.freelecspringboot.book.springboot.domain.posts;

import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @BeforeEach
    public void clean_up(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_저장_불러오기(){
        String title = "테스트 제목";
        String content = "테스트 본문";
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("spring")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTime_등록(){
        //given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("title")
                .author("author")
                .content("content").build());
        //when
        List<Posts> all = postsRepository.findAll();
        //then
        System.out.println(all.size());
        assertThat(all.get(0).getCreatedDate()).isAfter(now);
        assertThat(all.get(0).getModifiedDate()).isAfter(now);
        assertEquals(all.get(0).getCreatedDate(),all.get(0).getModifiedDate());
        System.out.println("DATE : "+all.get(0).getCreatedDate());
    }
}