package com.bruce.llm4j.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT, chatModel = "openAiChatModel", tools = {"functionTools"})
public interface Assistant {

    @SystemMessage("你的名字叫小赫，你是一个友好的助手。请用中文回答用户的问题。")
    String chat(String userMessage);

}