package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_cms_client";
    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    public static final String ROUTINGKEY = "#.client.#";

    //声明交换机
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_TOPIC() {
        return ExchangeBuilder.topicExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE2 = "ex_routing_cms_postpage2";

    //声明交换机
    @Bean(EX_ROUTING_CMS_POSTPAGE2)
    public Exchange EXCHANGE_TOPIC2() {
        return ExchangeBuilder.topicExchange(EX_ROUTING_CMS_POSTPAGE2).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_SM() {
        return new Queue(QUEUE_CMS_POSTPAGE);
    }

    //绑定交换机和队列
    @Bean
    public Binding binding(@Qualifier(QUEUE_CMS_POSTPAGE) Queue sm, @Qualifier(EX_ROUTING_CMS_POSTPAGE2) Exchange exchange) {
        return BindingBuilder.bind(sm).to(exchange).with(ROUTINGKEY).noargs();
    }
}
