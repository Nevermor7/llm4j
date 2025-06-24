package com.bruce.langchain4jlow.controller;


import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/langchain4j-low")
public class LangChain4JLowController {

    @Resource
    private OpenAiChatModel openAiChatModel;

    @GetMapping(value = "/test")
    public String test() {
        return "Hello, LangChain4J!";
    }

    @GetMapping(value = "/chat")
    public String chat(String prompt) {
        return openAiChatModel.chat(ChatRequest.builder().messages(UserMessage.from(prompt)).build()).aiMessage().text();
    }

}
