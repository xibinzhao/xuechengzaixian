package com.xuecheng.order.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.order.response.OrderCode;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.order.dao.repository.XcTaskHisRepository;
import com.xuecheng.order.dao.repository.XcTaskRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.Date;

@Configuration
public class MqListener {
    @Autowired
    private XcTaskRepository xcTaskRepository;
    @Autowired
    private XcTaskHisRepository xcTaskHisRepository;

    @RabbitListener(queues = {"xc_learning_finishaddchoosecourse"})
    public void list(String message) {
        XcTask task = getTask(message);
        if (task == null) {
            ExceptionCast.cast(OrderCode.ORDER_CREATE_ERROR);
        }
        update(task);
    }

    private XcTask getTask(String json) {
        XcTask xcTask = JSON.parseObject(json, XcTask.class);
        return xcTask;
    }

    private void delete(String taskId) {
        xcTaskRepository.deleteById(taskId);
    }

    @Transactional
    public void update(XcTask task) {
        String id = task.getId();
        XcTaskHis xcTaskHis = new XcTaskHis();
        xcTaskHis.setUpdateTime(new Date());
        delete(id);
        xcTaskHis.setDeleteTime(new Date());
        xcTaskHis.setCreateTime(new Date());
        xcTaskHis.setMqExchange(task.getMqExchange());
        xcTaskHis.setMqRoutingkey(task.getMqRoutingkey());
        xcTaskHis.setRequestBody(task.getRequestBody());
        xcTaskHis.setId(task.getId());
        xcTaskHis.setTaskType(task.getTaskType());
        xcTaskHis.setStatus("51001");
        xcTaskHisRepository.save(xcTaskHis);
    }
}
