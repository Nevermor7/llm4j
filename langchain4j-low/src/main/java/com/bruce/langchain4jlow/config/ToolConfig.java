package com.bruce.langchain4jlow.config;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolParameters;
import dev.langchain4j.agent.tool.ToolSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ToolConfig {

    @Bean
    public ToolSpecification weatherTool() {
        return ToolSpecification.builder()
                .name("getWeather")
                .description("返回给定城市的天气预报")
                .parameters(ToolParameters.builder()
                        .type("string")
                        .properties(new LinkedHashMap<String, Map<String, Object>>() {{
                            put("city", new LinkedHashMap<String, Object>() {{
                                put("type", "string");
                                put("description", "应返回天气预报的城市名称");
                            }});
                        }})
                        .required(Collections.singletonList("city"))
                        .build())
                .build();
    }

    @Bean
    public ToolSpecification invoiceTool() {
        return ToolSpecification.builder()
                .name("handleInvoice")
                .description("根据用户提交的开票信息，开具发票")
                .parameters(ToolParameters.builder()
                        .type("string")
                        .properties(new LinkedHashMap<String, Map<String, Object>>() {{
                            put("companyName", new LinkedHashMap<String, Object>() {{
                                put("type", "string");
                                put("description", "公司名称");
                            }});
                            put("dutyNumber", new LinkedHashMap<String, Object>() {{
                                put("type", "string");
                                put("description", "税号");
                            }});
                            put("amount", new LinkedHashMap<String, Object>() {{
                                put("type", "string");
                                put("description", "金额，保留两位有效数字");
                            }});
                        }})
                        .required(Collections.singletonList("companyName"))
                        .build())
                .build();
    }

    /**
     * langchain4j官方文档中提供的写法，但是由于版本的限制，获取不到工具的参数名
     */
    @Tool("返回给定城市的天气")
    public String getWeather(@P(value = "应返回天气预报的城市", required = true) String city) {
        // 调用气象网站API获取精确的天气信息
        log.info("city =>>>> {}", city);
        return "多云转晴";
    }

    @Tool("根据用户提交的开票信息，开具发票")
    public String handleInvoice(@P(value = "公司名称") String companyName,
                                @P(value = "税号", required = false) String dutyNumber,
                                @P(value = "金额，保留两位有效数字", required = false) String amount) {
        log.info("companyName =>>>> {} dutyNumber =>>>> {} amount =>>>> {}", companyName, dutyNumber, amount);
        return "开票成功";
    }

}
