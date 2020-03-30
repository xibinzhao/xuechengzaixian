package com.xuecheng.learning.test;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.learning.XcLearningCourse;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.learning.mq.MqListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    private MqListener mqListener;

    @Test
    public void tset() {
        mqListener.listener("{\"createTime\":1575362554618,\"id\":\"11\",\"mqExchange\":\"ex_learning_addchoosecourse\",\"mqRoutingkey\":\"addchoosecourse\",\"requestBody\":\"{\\\"courseId\\\":\\\"3333\\\",\\\"userId\\\":\\\"haha\\\"}\",\"taskType\":\"xc_learning_addchoosecourse\",\"updateTime\":1575362554618}");
    }
}
