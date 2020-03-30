package com.xuecheng.rabbitMQ.consumer.test;

import com.rabbitmq.client.*;
import com.xuecheng.rabbitMQ.consumer.utils.RabbitMqClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer01 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqClientUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("helloWorld", true, false, false, null);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             *
             * @param consumerTag  每一个消费者的标签
             * @param envelope  通过他可以获取到交换机
             *  String exchange = envelope.getExchange();
             * //路由key
             * String routingKey = envelope.getRoutingKey();
             * //消息id
             * long deliveryTag = envelope.getDeliveryTag();
             * @param properties   信息内容的 类型
             * @param body   信息体
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        };
        channel.basicConsume("helloWorld", true, consumer);
    }
}
