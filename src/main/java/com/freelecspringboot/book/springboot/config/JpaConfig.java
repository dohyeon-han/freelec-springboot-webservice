package com.freelecspringboot.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//HelloControllerTest에서 @WebMvcTest를 사용한다.
//이때 SpringbootApplication을 스캔 대상이 아니므로 @EnableJpaAuditing을 읽을 수 없다.
//@Configuration에 @EnableJpaAuditing을 생성해준다.
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
