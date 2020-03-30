package com.xuecheng.order.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.order.service.OrderSuccessService;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.utils.RabbitMqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MqTask {
    @Autowired
    private OrderSuccessService successService;
    @Autowired
    private RabbitMqUtils rabbitMqUtils;

    // @Scheduled(fixedDelay = 5000)  // 上次执行完毕后5秒执行
    // @Scheduled(initialDelay=3000, fixedRate=5000)  // 第一次延迟3秒，以后每隔5秒执行一次
    @Scheduled(fixedRate = 30000) // 上次执行开始时间后5秒执行 //
    public void sendOrderSuccessMessage() {
        List<XcTask> task = successService.findTask();
        for (XcTask xcTask : task) {
            String json = JSON.toJSONString(xcTask);
            rabbitMqUtils.sendRequest(json);
        }
    }
}
