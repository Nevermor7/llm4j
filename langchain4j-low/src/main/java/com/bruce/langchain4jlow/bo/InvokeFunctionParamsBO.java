package com.bruce.langchain4jlow.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 执行目标函数的参数对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvokeFunctionParamsBO {

    /**
     * 定义目标函数的类
     */
    private Class<?> functionHandleClass;

    /**
     * 定义目标函数的类的spring bean
     */
    private Object functionHandleBean;

    /**
     * 目标函数名称
     */
    private String functionName;

    /**
     * 目标函数入参JSON
     */
    private String argumentsJson;

}
