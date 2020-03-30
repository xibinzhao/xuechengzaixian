package com.xuecheng.rabbitMQ.consumer.test;

import com.rabbitmq.client.*;
import com.xuecheng.rabbitMQ.consumer.utils.RabbitMqClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer03_release {
    public static final String QUEUE_SM = "queue_sm";
    public static final String CHANGE_NAME = "change_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqClientUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
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
        channel.basicConsume(QUEUE_SM, true, consumer);
    }
}
