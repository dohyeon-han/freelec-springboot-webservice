package com.freelecspringboot.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//모든 entity의 상위 클래스
//createDate, modifiedDate 자동관리
@Getter
@MappedSuperclass //createdDate, modifiedDate도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) //Audit, 감시 기능
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
