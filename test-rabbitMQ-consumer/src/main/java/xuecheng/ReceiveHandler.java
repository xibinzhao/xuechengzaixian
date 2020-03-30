package xuecheng;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import xuecheng.rabbitmq.test.config.RabbitMqConfig;

@Component
public class ReceiveHandler {
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_EMAIL})
    public void print(String message) {
        System.out.println(message);
    }
}

