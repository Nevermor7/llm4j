package com.bruce.llm4j.config;

import com.bruce.llm4j.pojo.EquipmentPO;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FunctionTools {

    @Tool("根据用户提交的开票信息进行开票")
    public String invoiceHandler(@P("公司名称") String companyName, String dutyNumber, @P("金额保留两位有效数字") String amount) {
        log.info("companyName =>>>> {} dutyNumber =>>>> {} amount =>>>> {}", companyName, dutyNumber, amount);
        return "开票成功";
    }

    @Tool("如果用户想询问设备相关问题，可以使用此工具获取设备数据")
    public List<EquipmentPO> equipmentHandler(@P("日期，请格式化为2025-06-17这种格式")String date, @P(value = "设备名称", required = false)String name,
                                              @P(value = "设备状态：1-启用中，2-已停用，请根据用户输入的文字转换为数字状态码", required = false)int state,
                                              @P(value = "哪个园区的设备", required = false)String park) {
        EquipmentPO po1 = new EquipmentPO();
        po1.setId("001");
        po1.setName("过山车");
        po1.setState(1);
        po1.setPark("世界之窗");
        po1.setDate("2025-06-17");
        EquipmentPO po2 = new EquipmentPO();
        po2.setId("002");
        po2.setName("摩天轮");
        po2.setState(2);
        po2.setPark("欢乐谷");
        po2.setDate("2025-06-17");
        EquipmentPO po3 = new EquipmentPO();
        po3.setId("003");
        po3.setName("过山车");
        po3.setState(2);
        po3.setPark("世界之窗");
        po3.setDate("2025-06-16");
        EquipmentPO po4 = new EquipmentPO();
        po4.setId("004");
        po4.setName("摩天轮");
        po4.setState(1);
        po4.setPark("欢乐谷");
        po4.setDate("2025-06-16");
        return List.of(po1, po2, po3, po4).stream().map(po -> {
            if (!date.equals(po.getDate())) {
                return null;
            }
            if (StringUtils.hasText(name) && !po.getName().contains(name)) {
                return null;
            }
            if (state != po.getState()) {
                return null;
            }
            if (StringUtils.hasText(park) && !po.getPark().contains(park)) {
                return null;
            }
            return po;
        }).collect(Collectors.toList());
    }

}
