package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    //队列bean的名称
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE2 = "ex_routing_cms_postpage2";
    //队列的名称
    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;
    //routingKey    即站点Id
    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;

    //声明交换机
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_TOPIC() {
        return ExchangeBuilder.topicExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    //声明交换机
    @Bean(EX_ROUTING_CMS_POSTPAGE2)
    public Exchange EXCHANGE_TOPIC2() {
        return ExchangeBuilder.topicExchange(EX_ROUTING_CMS_POSTPAGE2).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_SM() {
        return new Queue(queue_cms_postpage_name);
    }

    //绑定交换机和队列
    @Bean
    public Binding binding(@Qualifier(QUEUE_CMS_POSTPAGE) Queue sm, @Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange) {
        return BindingBuilder.bind(sm).to(exchange).with(routingKey).noargs();
    }
}
