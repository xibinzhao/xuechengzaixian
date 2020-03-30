package com.xuecheng.order.dao.mapper;

import com.xuecheng.framework.domain.task.XcTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface XcTaskMapper {
    @Results(id = "xcTask", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "delete_time", property = "deleteTime"),
            @Result(column = "task_type", property = "taskType"),
            @Result(column = "mq_exchange", property = "mqExchange"),
            @Result(column = "mq_routingkey", property = "mqRoutingkey"),
            @Result(column = "request_body", property = "requestBody"),
            @Result(column = "version", property = "version"),
            @Result(column = "status", property = "status"),
            @Result(column = "errormsg", property = "errormsg")

    })
    @Select("select * from xc_task where create_time>#{time}")
    List<XcTask> findByCreateTime(String time);
}
