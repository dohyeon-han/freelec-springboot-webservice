package com.freelecspringboot.book.springboot.web;

import com.freelecspringboot.book.springboot.web.controller.HelloController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hello_return() throws Exception {
        mockMvc.perform(get("/hello"))//hello로 get 요청
                .andExpect(status().isOk())// 200
                .andExpect(content().string("hello"));// controller가 hello return
    }

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