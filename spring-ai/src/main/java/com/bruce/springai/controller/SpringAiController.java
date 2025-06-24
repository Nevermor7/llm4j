package com.bruce.springai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-ai")
public class SpringAiController {

    @GetMapping(value = "/test")
    public String test() {
        return "Hello, Spring AI!";
    }

}
