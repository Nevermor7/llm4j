package com.bruce.llm4j.pojo;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
public class EquipmentPO {

    @Description("设备id")
    private String id;

    @Description("设备名称")
    private String name;

    @Description("设备状态：1-启用中，2-已停用")
    private Integer state;

    @Description("所属园区")
    private String park;

    @Description("检修日期：格式为yyyy-MM-dd")
    private String date;

}
