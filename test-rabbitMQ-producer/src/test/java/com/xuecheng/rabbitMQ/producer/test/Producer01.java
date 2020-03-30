package com.xuecheng.rabbitMQ.producer.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xuecheng.rabbitMQ.consumer.utils.RabbitMqClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer01 {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitMqClientUtils.getConnection();
            channel = RabbitMqClientUtils.getChannel(connection);
            /**
             * 声明队列
             * queue  第一个参数为队列的名称
             * 第二个参数为  是否持久化
             * 第三个参数为  是否独占此连接   连接内不允许访问其他的队列 相对的  其他的连接也不能访问此队列
             * 第四个参数为  队列是否在不使用时进行删除  就是在服务重启后还存在吗
             * 第五个参数为  队列的其他配置 如果又需要可以了解
             */
            channel.queueDeclare("helloWorld", true, false, false, null);
            /**
             * 发送信息到队列
             * 第一个参数  指定交换机
             * 第二个参数  指定队列的 通过名称指定
             * 第三个参数  信息的内容是什么类型
             * 第四个参数  为信息体但是需要转换为字节数组
             */
            channel.basicPublish("", "helloWorld", null, "bendan".getBytes());
            System.out.println("发送成功");
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
