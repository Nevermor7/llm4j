package com.bruce.langchain4jhigh.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
public class SystemMessageCache {

    private static volatile String systemMessage = "";
    private static volatile long lastModified = 0L;
    private static final String FILE_NAME = "system_message.txt";

    // 启动时加载
    static {
        loadSystemMessage();
    }

    private static void loadSystemMessage() {
        try {
            ClassPathResource resource = new ClassPathResource(FILE_NAME);
            File file = resource.getFile();
            long modified = file.lastModified();
            if (modified != lastModified) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                systemMessage = new String(bytes, StandardCharsets.UTF_8);
                lastModified = modified;
            }
        } catch (Exception e) {
            systemMessage = "";
        }
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    // 每分钟检查一次文件是否有变化
    @Scheduled(fixedDelay = 30000)
    public void refreshIfChanged() {
        loadSystemMessage();
    }

}
