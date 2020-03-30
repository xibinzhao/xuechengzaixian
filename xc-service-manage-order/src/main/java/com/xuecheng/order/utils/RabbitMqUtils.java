package com.xuecheng.order.utils;

import com.xuecheng.order.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqUtils {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendRequest(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EX_LEARNING_ADDCHOOSECOURSE, RabbitMQConfig.XC_LEARNING_ADDCHOOSECOURSE_KEY, message);
    }
}
