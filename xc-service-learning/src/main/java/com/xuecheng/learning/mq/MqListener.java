package com.xuecheng.learning.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.learning.XcLearningCourse;
import com.xuecheng.framework.domain.order.response.OrderCode;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.learning.config.RabbitMqConfig;
import com.xuecheng.learning.dao.repository.XcLearningCourseRepository;
import com.xuecheng.learning.dao.repository.XcTaskHisRepository;
import com.xuecheng.learning.utils.RabbitMqUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Component
public class MqListener {
    @Autowired
    private RabbitMqUtils rabbitMqUtils;
    @Autowired
    private XcLearningCourseRepository xcLearningCourseRepository;
    @Autowired
    private XcTaskHisRepository xcTaskHisRepository;

    @RabbitListener(queues = {"xc_learning_addchoosecourse"})
    public void listener(String message) {
        XcTask task = getTask(message);
        if (task == null) {
            ExceptionCast.cast(OrderCode.ORDER_CREATE_ERROR);
        }
        boolean exist = isExist(task);
        if (exist) {
            rabbitMqUtils.sendSuccess(message);
            return;
        }
        XcTaskHis xcTaskHis = saveTaskHis(task);
        saveCourseLearning(xcTaskHis);
        rabbitMqUtils.sendSuccess(message);
    }

    private XcTask getTask(String json) {
        XcTask xcTask = JSON.parseObject(json, XcTask.class);
        return xcTask;
    }

    private XcTaskHis saveTaskHis(XcTask xcTask) {

        XcTaskHis xcTaskHis = new XcTaskHis();
        xcTaskHis.setCreateTime(new Date());
        xcTaskHis.setMqExchange(RabbitMqConfig.EX_LEARNING_ADDCHOOSECOURSE);
        xcTaskHis.setMqRoutingkey(RabbitMqConfig.XC_LEARNING_FINISHADDCHOOSECOURSE_KEY);
        xcTaskHis.setRequestBody(xcTask.getRequestBody());
        xcTaskHis.setId(xcTask.getId());
        xcTaskHis.setTaskType(RabbitMqConfig.XC_LEARNING_FINISHADDCHOOSECOURSE);
        //51002为未完成
        //51001为完成
        xcTaskHis.setStatus("51002");
        XcTaskHis save = xcTaskHisRepository.save(xcTaskHis);
        return save;
    }

    private boolean isExist(XcTask xcTask) {
        Optional<XcTaskHis> op = xcTaskHisRepository.findById(xcTask.getId());
        if (op.isPresent()) {
            XcTaskHis xcTaskHis = op.get();
            if (xcTaskHis.getStatus().equals("51001")) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void saveCourseLearning(XcTaskHis xcTaskHis) {
        String requestBody = xcTaskHis.getRequestBody();
        if (StringUtils.isEmpty(requestBody)) {
            ExceptionCast.cast(OrderCode.ORDER_CREATE_ERROR);
        }
        XcLearningCourse xcLearningCourse = JSON.parseObject(requestBody, XcLearningCourse.class);
        if (StringUtils.isNotEmpty(xcLearningCourse.getCourseId()) && StringUtils.isNotEmpty(xcLearningCourse.getUserId())) {
            XcLearningCourse xcLearningCourse1 = xcLearningCourseRepository.findByCourseIdAndUserId(xcLearningCourse.getCourseId(), xcLearningCourse.getUserId());
            if (xcLearningCourse1 == null) {
                xcLearningCourse.setId(xcLearningCourse.getCourseId());
                XcLearningCourse save = xcLearningCourseRepository.save(xcLearningCourse);
            }
            xcTaskHis.setStatus("51001");
            xcTaskHisRepository.save(xcTaskHis);
            return;
        }
        ExceptionCast.cast(OrderCode.ORDER_CREATE_ERROR);
    }
}
