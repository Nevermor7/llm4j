package com.bruce.langchain4jhigh.controller;

import com.bruce.langchain4jhigh.service.Assistant;
import com.bruce.langchain4jhigh.service.StreamingAssistant;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/langchain4j-high")
public class LangChain4JHighController {

    @Resource
    private Assistant assistant;

    @Resource
    private StreamingAssistant streamingAssistant;

    @GetMapping("/chat")
    public String naiveChat(@RequestParam("prompt") String prompt) {
        return assistant.chat(prompt);
    }

    @GetMapping(value = "/chat/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxChat(@RequestParam("prompt") String prompt) {
        return streamingAssistant.chat(prompt);
    }

}
