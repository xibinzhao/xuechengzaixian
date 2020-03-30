package com.xuecheng.order;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.learning.XcLearningCourse;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.config.RabbitMQConfig;
import com.xuecheng.order.dao.mapper.XcTaskMapper;
import com.xuecheng.order.dao.repository.XcTaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    private XcTaskMapper xcTaskMapper;
    @Autowired
    private XcTaskRepository xcTaskRepository;

    @Test
    public void test() {
        Date date = new Date(new Date().getTime() - 1000 * 600);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        List<XcTask> byCreateTime = xcTaskMapper.findByCreateTime(format);
        System.out.println(byCreateTime);
    }

    @Test
    public void test2() {
        XcLearningCourse xcLearningCourse = new XcLearningCourse();
        xcLearningCourse.setCourseId("3333");
        xcLearningCourse.setUserId("haha");
        XcTask xcTask = new XcTask();
        xcTask.setCreateTime(new Date());
        xcTask.setMqExchange(RabbitMQConfig.EX_LEARNING_ADDCHOOSECOURSE);
        xcTask.setMqRoutingkey(RabbitMQConfig.XC_LEARNING_ADDCHOOSECOURSE_KEY);
        xcTask.setTaskType(RabbitMQConfig.XC_LEARNING_ADDCHOOSECOURSE);
        xcTask.setUpdateTime(new Date());
        xcTask.setRequestBody(JSON.toJSONString(xcLearningCourse));
        xcTaskRepository.save(xcTask);
    }

    @Test
    public void test3() {
        XcLearningCourse xcLearningCourse = new XcLearningCourse();
        xcLearningCourse.setCourseId("3333");
        xcLearningCourse.setUserId("haha");
        XcTask xcTask = new XcTask();
        xcTask.setCreateTime(new Date());
        xcTask.setMqExchange(RabbitMQConfig.EX_LEARNING_ADDCHOOSECOURSE);
        xcTask.setMqRoutingkey(RabbitMQConfig.XC_LEARNING_ADDCHOOSECOURSE_KEY);
        xcTask.setTaskType(RabbitMQConfig.XC_LEARNING_ADDCHOOSECOURSE);
        xcTask.setUpdateTime(new Date());
        xcTask.setRequestBody(JSON.toJSONString(xcLearningCourse));
        xcTask.setId("11");
        String s = JSON.toJSONString(xcTask);
        System.out.println(s);
    }
}
