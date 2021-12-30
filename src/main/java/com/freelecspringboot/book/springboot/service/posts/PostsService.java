package com.freelecspringboot.book.springboot.service.posts;

import com.freelecspringboot.book.springboot.domain.posts.Posts;
import com.freelecspringboot.book.springboot.domain.posts.PostsRepository;
import com.freelecspringboot.book.springboot.web.dto.PostsListResponseDto;
import com.freelecspringboot.book.springboot.web.dto.PostsResponseDto;
import com.freelecspringboot.book.springboot.web.dto.PostsSaveRequestDto;
import com.freelecspringboot.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public List<Posts> findAll(){
        return postsRepository.findAll();
    }

    public Optional<Posts> findOne(Long id){
        return postsRepository.findById(id);
    }

    //영속성 컨텍스트
    //update 쿼리가 없어도 엔티티 변경 후 트렌젝션이 끝나면 변경된 부분이 반영
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts post = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        post.update(requestDto.getTitle(),requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->
            new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        postsRepository.deleteById(id);
    }
}
