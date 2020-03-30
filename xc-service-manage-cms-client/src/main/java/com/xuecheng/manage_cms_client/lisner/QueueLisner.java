package com.xuecheng.manage_cms_client.lisner;

import com.alibaba.fastjson.JSON;
import com.xuecheng.manage_cms_client.service.PageLoderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QueueLisner {

    @Autowired
    private PageLoderService pageLoderService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void lisner(String message) {
        System.out.println(message);
        Map map = JSON.parseObject(message, Map.class);
        String pageId = (String) map.get("pageId");
        pageLoderService.savePageToServerPath(pageId);
    }
}
