package com.xuecheng.manage_cms.feignClient;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.learning.XcLearningList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "XC-SERVICE-MANAGE-COURSE")
public interface CourseBaseClient {
    @GetMapping("/course/coursebase/getById/{id}")
    CourseBase findById(@PathVariable("id") String courseId);
}
