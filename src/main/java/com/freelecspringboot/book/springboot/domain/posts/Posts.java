package com.freelecspringboot.book.springboot.domain.posts;

import com.freelecspringboot.book.springboot.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

// @Setter //Entity 클래스는 setter를 만들지 않는다.
@Getter
@NoArgsConstructor
@Entity // 테이블과 연결될 클래스
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
