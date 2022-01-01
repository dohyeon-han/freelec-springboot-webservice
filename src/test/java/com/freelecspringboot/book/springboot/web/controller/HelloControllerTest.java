package com.freelecspringboot.book.springboot.web.controller;

import com.freelecspringboot.book.springboot.config.auth.CustomOAuth2UserService;
import com.freelecspringboot.book.springboot.config.auth.SecurityConfig;
import com.freelecspringboot.book.springboot.web.controller.HelloController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //SecurityConfig는 CustomOAuth2UserService를 필요로 한다.
    //@WebMvcTest는 @Service를 스캔할 수 없다
    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @WithMockUser(roles = "USER")
    @Test
    void hello_return() throws Exception {
        mockMvc.perform(get("/hello"))//hello로 get 요청
                .andExpect(status().isOk())// 200
                .andExpect(content().string("hello"));// controller가 hello return
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto_retuen()throws Exception{
        String name = "spring";
        int amount = 10;

        mockMvc.perform(
                get("/hello/dto")
                    .param("name",name)
                        .param("amount",String.valueOf(amount)
                        )
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(name)))
                .andExpect(jsonPath("$.amount", Matchers.is(amount)));
    }
}