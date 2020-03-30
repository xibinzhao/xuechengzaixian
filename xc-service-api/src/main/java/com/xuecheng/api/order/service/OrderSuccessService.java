package com.xuecheng.api.order.service;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;

import java.util.List;

public interface OrderSuccessService {
    List<XcTask> findTask();

    void sendSuccess();

    XcTask findXcTask(String id);
}
