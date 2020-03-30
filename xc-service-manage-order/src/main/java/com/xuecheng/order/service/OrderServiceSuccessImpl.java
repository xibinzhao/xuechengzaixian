package com.xuecheng.order.service;

import com.xuecheng.api.order.service.OrderSuccessService;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.order.dao.mapper.XcTaskMapper;
import com.xuecheng.order.dao.repository.XcTaskHisRepository;
import com.xuecheng.order.dao.repository.XcTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceSuccessImpl implements OrderSuccessService {
    @Autowired
    private XcTaskRepository xcTaskRepository;
    @Autowired
    private XcTaskHisRepository xcTaskHisRepository;
    @Autowired
    private XcTaskMapper xcTaskMapper;

    @Override
    public List<XcTask> findTask() {
        Date date = new Date(new Date().getTime() - 1000 * 600);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return xcTaskMapper.findByCreateTime(format);
    }

    @Override
    public void sendSuccess() {

    }

    @Override
    public XcTask findXcTask(String id) {
        Optional<XcTask> op = xcTaskRepository.findById(id);
        if (op.isPresent()) {
            return op.get();
        }
        return null;
    }

    @Transactional
    public void requestSuccess(String requesId) {
        XcTask xcTask = findXcTask(requesId);
        XcTaskHis xcTaskHis = new XcTaskHis();
        xcTaskHisRepository.save(xcTaskHis);
    }
}
