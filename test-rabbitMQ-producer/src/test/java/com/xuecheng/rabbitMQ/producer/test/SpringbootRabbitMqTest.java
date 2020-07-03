package com.xuecheng.rabbitMQ.producer.test;

import com.xuecheng.rabbitmq.test.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class SpringbootRabbitMqTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 交换机名称
     * routing  路由
     * message 内容
     */
    @Test
    public void testSend() {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPIC, "dasda.email.dasd", "send  email to user");
    }

}
