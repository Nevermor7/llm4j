package com.bruce.langchain4jhigh;

import com.bruce.langchain4jhigh.service.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssistantTest {

    @Autowired
    private Assistant assistant;

    @Test
    void testChatAssistantMemoryCapability() {
        String chat = assistant.chat("你好，我的名字叫Bruce");
        System.out.println(chat);
    }

}
