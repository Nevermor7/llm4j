package com.bruce.llm4j.config;

import com.bruce.llm4j.service.FunctionAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LLMConfig {

    @Value("${langchain4j.open-ai.chat-model.base-url}")
    private String baseUrl;
    @Value("${langchain4j.open-ai.chat-model.model-name}")
    private String modelName;

    @Bean
    public ChatModel functionAssistantModel() {
        return OpenAiChatModel.builder()
                .modelName(modelName)  // 设置使用的模型名称
                .logRequests(true)
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public FunctionAssistant functionAssistant(ChatModel functionAssistantModel) {
        return AiServices.builder(FunctionAssistant.class)
                .chatModel(functionAssistantModel)
                .tools(new FunctionTools())
                .build();
    }

}
