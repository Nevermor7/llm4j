package com.bruce.langchain4jlow.config;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LLMConfig {

    @Value("${langchain4j.open-ai.chat-model.base-url}")
    private String baseUrl;
    @Value("${langchain4j.open-ai.chat-model.model-name}")
    private String modelName;
    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String apiKey;

    @Bean
    public OpenAiChatModel functionAssistantModel() {
        return OpenAiChatModel.builder()
                .modelName(modelName)  // 设置使用的模型名称
                .logRequests(true)
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();
    }

}
