package com.xuecheng.manage_cms_client.utils;

import com.xuecheng.manage_cms_client.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqUtils {
    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String routingKey, String stutus, String pageId) {
        String info = "{'html':'" + "','queueName':'" + queue_cms_postpage_name + "','stutus':'" + stutus + "','pageId':'" + pageId + "'}";
        rabbitTemplate.convertAndSend(RabbitMqConfig.EX_ROUTING_CMS_POSTPAGE2, routingKey, info);
    }
}
