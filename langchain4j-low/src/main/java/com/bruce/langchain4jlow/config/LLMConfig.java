package com.bruce.langchain4jlow.config;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LLMConfig {

    @Value("${langchain4j.open-ai.chat-model.base-url}")
    private String openAiBaseUrl;
    @Value("${langchain4j.open-ai.chat-model.model-name}")
    private String openAiModelName;
    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String openAiApiKey;

    @Value("${langchain4j.open-ai.streaming-chat-model.base-url}")
    private String openAiStreamingBaseUrl;
    @Value("${langchain4j.open-ai.streaming-chat-model.model-name}")
    private String openAiStreamingModelName;
    @Value("${langchain4j.open-ai.streaming-chat-model.api-key}")
    private String openAiStreamingApiKey;

    @Value("${langchain4j.ollama.chat-model.base-url}")
    private String ollamaBaseUrl;
    @Value("${langchain4j.ollama.chat-model.model-name}")
    private String ollamaModelName;

    @Value("${langchain4j.ollama.streaming-chat-model.base-url}")
    private String ollamaStreamingBaseUrl;
    @Value("${langchain4j.ollama.streaming-chat-model.model-name}")
    private String ollamaStreamingModelName;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(20);
    }

    @Bean
    public ConversationalChain chain(ChatMemory chatMemory, OpenAiChatModel chatModel) {
        return ConversationalChain.builder()
                .chatLanguageModel(chatModel)
                .chatMemory(chatMemory)
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "langchain4j.ollama", name = "enable", havingValue = "true")
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .modelName(ollamaModelName)
                .baseUrl(ollamaBaseUrl)
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "langchain4j.ollama", name = "enable", havingValue = "true")
    public OllamaStreamingChatModel ollamaStreamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .modelName(ollamaStreamingModelName)
                .baseUrl(ollamaStreamingBaseUrl)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(ChatLanguageModel.class)
    public OpenAiChatModel openAiChatModel() {
        return OpenAiChatModel.builder()
                .modelName(openAiModelName)
                .logRequests(true)
                .apiKey(openAiApiKey)
                .baseUrl(openAiBaseUrl).parallelToolCalls(true)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(StreamingChatLanguageModel.class)
    public OpenAiStreamingChatModel openAiStreamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .modelName(openAiStreamingModelName)
                .logRequests(true)
                .apiKey(openAiStreamingApiKey)
                .baseUrl(openAiStreamingBaseUrl)
                .build();
    }

}
