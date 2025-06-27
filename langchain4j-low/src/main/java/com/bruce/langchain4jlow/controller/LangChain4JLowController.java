package com.bruce.langchain4jlow.controller;


import com.bruce.langchain4jlow.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/langchain4j-low")
public class LangChain4JLowController {

    @Resource
    private ChatService chatServiceImpl;

    @GetMapping(value = "/test")
    public String test() {
        return "Hello, LangChain4J!";
    }

    @GetMapping(value = "/chat")
    public String chat(String prompt) {
        return chatServiceImpl.chat(prompt);
    }

}
