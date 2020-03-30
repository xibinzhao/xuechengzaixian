package com.xuecheng.order.dao.repository;

import com.xuecheng.framework.domain.task.XcTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface XcTaskRepository extends JpaRepository<XcTask, String> {

}
