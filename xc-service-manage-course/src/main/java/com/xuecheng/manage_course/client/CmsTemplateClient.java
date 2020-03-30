package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "XC-SERVICE-MANAGE-CMS")
@Component
public interface CmsTemplateClient {
    @PostMapping("/cms/temp/add")
    ResponseResult add(@RequestParam("info") String info, @RequestParam("fileName") String fileName, @RequestBody CmsTemplate template);

    @GetMapping("/cms/temp/list")
    QueryResponseResult findAll();
}
