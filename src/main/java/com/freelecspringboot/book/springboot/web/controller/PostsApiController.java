package com.freelecspringboot.book.springboot.web.controller;

import com.freelecspringboot.book.springboot.domain.posts.Posts;
import com.freelecspringboot.book.springboot.service.posts.PostsService;
import com.freelecspringboot.book.springboot.web.dto.PostsSaveRequestDto;
import com.freelecspringboot.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @GetMapping("/api/v1/posts")
    public List<Posts> findAll(){
        return postsService.findAll();
    }

    @GetMapping("/api/v1/posts/{id}")
    public Optional<Posts> findById(@PathVariable Long id){
        return postsService.findOne(id);
    }

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id,requestDto);
    }
}
