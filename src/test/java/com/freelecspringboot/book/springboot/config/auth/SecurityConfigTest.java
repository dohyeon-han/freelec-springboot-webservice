package com.freelecspringboot.book.springboot.config.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityConfigTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void profile_호출() throws Exception{
        String expected = "default";
        ResponseEntity<String> response =
                restTemplate.getForEntity("/profile",String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), expected);
    }
}