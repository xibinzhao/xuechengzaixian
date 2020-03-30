package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.controller.CoursePicControllerApi;
import com.xuecheng.api.course.service.CoursePicService;
import com.xuecheng.framework.domain.course.response.CoursePicResult;
import com.xuecheng.framework.model.response.Response;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/coursepic/")
public class CourPicController implements CoursePicControllerApi {
    @Autowired
    private CoursePicService coursePicService;

    @Override
    @GetMapping("add")
    public ResponseResult add(String courseId, String pic) {
        return coursePicService.add(courseId, pic);
    }

    @PreAuthorize("hasAuthority('xc_teachmanager_course_picList')")
    @Override
    @GetMapping("list/{id}")
    public CoursePicResult getByCourseId(@PathVariable("id") String courseId) {
        return coursePicService.getByCourseId(courseId);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseResult deleteById(String courseId) {
        return coursePicService.deleteById(courseId);
    }
}
