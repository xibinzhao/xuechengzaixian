package com.xuecheng.manage_cms.lisner;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.cms.service.CmsPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class QueueLisner {
    @Autowired
    private CmsPageService cmsPageService;
    public static final Logger logger = LoggerFactory.getLogger(Qualifier.class);

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void lisner(String message) {
        Map map = JSON.parseObject(message, Map.class);
        String queueName = (String) map.get("queueName");
        String pageId = (String) map.get("pageId");
        String html = (String) map.get("html");

        String stutus = (String) map.get("stutus");
    }

    public void queueLoderErrorNoEquals(String queueName, String html, String pageId) {
        String html1 = cmsPageService.pageHtml(pageId);
        if (!html1.equals(html)) {
            logger.error(queueName + "----queueLoderErrorNoEquals");
        }
    }

    public void queueLoderErrorNotLoder(String queueName, String stutus) {
        if (stutus.equals("exception")) {
            logger.error(queueName + "----queueLoderErrorNotLoder");
        }
    }
}
