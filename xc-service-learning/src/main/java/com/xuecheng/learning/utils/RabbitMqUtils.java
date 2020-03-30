package com.xuecheng.learning.utils;

import com.xuecheng.learning.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqUtils {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSuccess(String messge) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EX_LEARNING_ADDCHOOSECOURSE, RabbitMqConfig.XC_LEARNING_FINISHADDCHOOSECOURSE_KEY, messge);
    }
}
