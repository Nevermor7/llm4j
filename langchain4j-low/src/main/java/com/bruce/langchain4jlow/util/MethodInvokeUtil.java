package com.bruce.langchain4jlow.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bruce.langchain4jlow.bo.InvokeFunctionParamsBO;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;

/**
 * 函数调用工具类
 * 用于动态调用指定类中的方法
 * 默认函数入参为Map,出参为Object
 */
@Slf4j
public class MethodInvokeUtil {

    public static String invokeFunction(InvokeFunctionParamsBO bo) {
        Map<String, String> argumentsMap = JSON.parseObject(bo.getArgumentsJson(), new TypeReference<Map<String, String>>() {});
        MethodType methodType = MethodType.methodType(Object.class, Map.class);
        try {
            MethodHandle methodHandle = MethodHandles.lookup().findVirtual(bo.getFunctionHandleClass(), bo.getFunctionName(), methodType).bindTo(bo.getFunctionHandleBean());
            return JSON.toJSONString(methodHandle.invokeExact(argumentsMap));
        } catch (Throwable e) {
            log.info("函数执行异常：{}", e.getMessage());
            return "";
        }
    }

}
