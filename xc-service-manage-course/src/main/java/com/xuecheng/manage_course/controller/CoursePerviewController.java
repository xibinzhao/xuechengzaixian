package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.controller.CoursePreviewControllerApi;
import com.xuecheng.api.course.service.CoursePerviewService;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/course/preview/")
public class CoursePerviewController extends BaseController implements CoursePreviewControllerApi {
    @Autowired
    private CoursePerviewService coursePreviewService;

    @Override
    @GetMapping("getModel/{id}")
    public CourseView getById(@PathVariable("id") String courseId) {
        return coursePreviewService.getById(courseId);
    }

    @GetMapping("show/{id}")
    @Override
    public CmsPage perview(@PathVariable("id") String courseId) {
        return coursePreviewService.perview(courseId);
    }

    /*发布课程*/
    ///course/preview/publish/'/course/preview/publish/'+id
    @GetMapping("/publish/{id}")
    @Override
    public CmsPageResult publish(@PathVariable("id") String courseId) {
        return coursePreviewService.publish(courseId);
    }

}
