package com.bruce.langchain4jhigh.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT, streamingChatModel = "openAiStreamingChatModel", chatMemory = "chatMemory", tools = {"functionTools"})
public interface StreamingAssistant {

    @SystemMessage("你的名字叫阿福，你是一个友好的助手。请用中文回答用户的问题。")
    Flux<String> chat(String userMessage);

}
