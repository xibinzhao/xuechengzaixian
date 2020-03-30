package com.xuecheng.rabbitMQ.producer.test;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xuecheng.rabbitMQ.consumer.utils.RabbitMqClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer02_release {
    public static final String QUEUE_EMAIL = "queue_email";
    public static final String QUEUE_SM = "queue_sm";
    public static final String CHANGE_NAME = "change_name";

    public static void main(String[] args) {
        Channel channel = null;
        Connection connection = null;
        try {
            connection = RabbitMqClientUtils.getConnection();
            channel = RabbitMqClientUtils.getChannel(connection);
            channel.queueDeclare(QUEUE_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_SM, true, false, false, null);
            //声明交换机
            /**
             * 参数
             * 1、声明交换机名称
             * 2、声明交换机类型
             *      交换机类型
             *          1、FANOUT  订阅者发布模式 使用
             *          2.TOPIC  通配符模式使用
             *          3、direct 路由模式使用
             *          4.headers   headers 模式使用
             */
            channel.exchangeDeclare(CHANGE_NAME, BuiltinExchangeType.FANOUT);
            /**
             *绑定队列到交换机
             * 参数
             * 1、队列名称
             * 2、交换机名称
             * 3、routekey   他是路由模式使用的参数  此时设置为空
             */
            channel.queueBind(QUEUE_SM, CHANGE_NAME, "");
            channel.queueBind(QUEUE_EMAIL, CHANGE_NAME, "");

            for (int i = 0; i < 5; i++) {
                String message = "send message to user" + i;
                channel.basicPublish(CHANGE_NAME, "", null, message.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

}
