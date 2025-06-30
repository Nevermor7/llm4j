package com.bruce.langchain4jlow.service.impl;

import com.bruce.langchain4jlow.bo.InvokeFunctionParamsBO;
import com.bruce.langchain4jlow.service.ChatService;
import com.bruce.langchain4jlow.service.FunctionCallingHandler;
import com.bruce.langchain4jlow.util.MethodInvokeUtil;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatLanguageModel chatModel;

    @Resource
    private List<ToolSpecification> toolSpecifications;

    @Resource
    private FunctionCallingHandler functionCallingHandler;

    @Resource
    private ConversationalChain chain;

    @Resource
    private ChatMemory chatMemory;

    @Override
    public String chat(String prompt) {
        AiMessage aiMessage = null;
        List<ChatMessage> messages = new ArrayList<>(chatMemory.messages());
        UserMessage userMessage = UserMessage.from(prompt);
        messages.add(userMessage);
        chatMemory.add(userMessage);
        if (chatModel instanceof OllamaChatModel) {
            aiMessage = chatModel.generate(Collections.singletonList(userMessage), toolSpecifications).content();
        } else if (chatModel instanceof OpenAiChatModel) {
            ChatRequest request = ChatRequest.builder()
                    .messages(messages)
                    .toolSpecifications(toolSpecifications)
                    .build();
            aiMessage = chatModel.chat(request).aiMessage();
        }
        log.info("AI Response: {}", aiMessage);
        if (aiMessage == null) {
            return "服务器异常，请稍后再试";
        }
        if (CollectionUtils.isEmpty(aiMessage.toolExecutionRequests())) {
            chatMemory.add(aiMessage);
            return aiMessage.text();
        }
        InvokeFunctionParamsBO bo = InvokeFunctionParamsBO.builder()
                .functionHandleClass(FunctionCallingHandler.class)
                .functionHandleBean(functionCallingHandler)
                .build();
        for (ToolExecutionRequest toolExecutionRequest : aiMessage.toolExecutionRequests()) {
            bo.setFunctionName(toolExecutionRequest.name());
            bo.setArgumentsJson(toolExecutionRequest.arguments());
            ToolExecutionResultMessage toolExecution = ToolExecutionResultMessage.from(toolExecutionRequest, MethodInvokeUtil.invokeFunction(bo));
            messages.add(toolExecution);
        }
        ChatRequest followRequest = ChatRequest.builder()
                .messages(messages)
                .toolSpecifications(toolSpecifications)
                .build();
        AiMessage aiMessage1 = chatModel.chat(followRequest).aiMessage();
        chatMemory.add(aiMessage1);
        return aiMessage1.text();
    }

}
