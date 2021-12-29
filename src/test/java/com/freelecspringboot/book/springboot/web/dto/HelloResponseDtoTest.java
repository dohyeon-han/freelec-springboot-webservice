package com.freelecspringboot.book.springboot.web.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloResponseDtoTest {
    @Test
    public void lombok_test(){
        String name = "spring";
        int amount = 10;

        HelloResponseDto helloResponseDto = new HelloResponseDto(name, amount);

        Assertions.assertEquals(helloResponseDto.getAmount(),amount);
        Assertions.assertEquals(helloResponseDto.getName(),name);
    }
}