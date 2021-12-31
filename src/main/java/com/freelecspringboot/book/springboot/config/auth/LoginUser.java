package com.freelecspringboot.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //파라미터 객체에만 선언 가능
@Retention(RetentionPolicy.RUNTIME) //Runtime에도 애노테이션 적용
public @interface LoginUser {

}
