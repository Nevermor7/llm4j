package com.bruce.llm4j.controller;

import com.bruce.llm4j.service.Assistant;
import com.bruce.llm4j.service.StreamingAssistant;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/langchain4j/chat")
public class LangChain4JController {

    @Resource
    private Assistant assistant;

    @Resource
    private StreamingAssistant streamingAssistant;

    @GetMapping
    public String naiveChat(@RequestParam("prompt") String prompt) {
        return assistant.chat(prompt);
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxChat(@RequestParam("prompt") String prompt) {
        return streamingAssistant.chat(prompt);
    }

}
