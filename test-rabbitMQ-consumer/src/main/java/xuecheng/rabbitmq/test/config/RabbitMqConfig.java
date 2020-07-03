package xuecheng.rabbitmq.test.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String QUEUE_EMAIL = "queue_email_topic";
    public static final String QUEUE_SM = "queue_sm_topic";
    public static final String EXCHANGE_TOPIC = "exchange_topic";
    public static final String EMAIL_ROUTING = "#.email.#";
    public static final String SMS_ROUTING = "#.sms.#";

    //声明交换机
    @Bean(EXCHANGE_TOPIC)
    public Exchange EXCHANGE_TOPIC() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_SM)
    public Queue QUEUE_SM() {
        return new Queue(QUEUE_SM);
    }
    //声明队列
    @Bean(QUEUE_EMAIL)
    public Queue QUEUE_EMAIL() {
        return new Queue(QUEUE_EMAIL);
    }

    //绑定交换机和队列
    @Bean
    public Binding binding(@Qualifier(QUEUE_SM) Queue sm, @Qualifier(EXCHANGE_TOPIC) Exchange exchange) {
        return BindingBuilder.bind(sm).to(exchange).with(SMS_ROUTING).noargs();
    }
    //绑定交换机和队列
    @Bean
    public Binding binding2(@Qualifier(QUEUE_EMAIL) Queue sm, @Qualifier(EXCHANGE_TOPIC) Exchange exchange) {
        return BindingBuilder.bind(sm).to(exchange).with(EMAIL_ROUTING).noargs();
    }
}
