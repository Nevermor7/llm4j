package com.bruce.llm4j.config;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FunctionTools {

    @Tool("根据用户提交的开票信息进行开票")
    public String amountHandle(@P("公司名称") String companyName, String dutyNumber,@P("金额保留两位有效数字") String amount) {
        log.info("companyName =>>>> {} dutyNumber =>>>> {} amount =>>>> {}", companyName, dutyNumber, amount);
        return "开票成功";
    }

}
