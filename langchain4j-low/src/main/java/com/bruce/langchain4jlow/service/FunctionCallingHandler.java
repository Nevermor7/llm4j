package com.bruce.langchain4jlow.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class FunctionCallingHandler {

    public Object getWeather(Map<String, String> argumentsMap) {
        // 调用气象网站API获取精确的天气信息
        log.info(JSON.toJSONString(argumentsMap));
        return "多云转晴";
    }

    public Object handleInvoice(Map<String, String> argumentsMap) {
        // 执行开票业务逻辑
        log.info(JSON.toJSONString(argumentsMap));
        return "开票成功";
    }

}
