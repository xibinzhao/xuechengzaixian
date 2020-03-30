package com.xuecheng.rabbitMQ.consumer.test;

import com.rabbitmq.client.*;
import com.xuecheng.rabbitMQ.consumer.utils.RabbitMqClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer02_release {
    public static final String QUEUE_EMAIL = "queue_email";
    public static final String CHANGE_NAME = "change_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqClientUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_EMAIL, true, false, false, null);
        channel.exchangeDeclare(CHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.queueBind(QUEUE_EMAIL, CHANGE_NAME, "");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        };
        channel.basicConsume(QUEUE_EMAIL, true, consumer);
    }
}
