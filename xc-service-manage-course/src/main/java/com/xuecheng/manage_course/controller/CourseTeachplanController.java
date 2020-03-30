package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.controller.CourseTeachplanControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseTeachplanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("course/teachplan")
@RestController
public class CourseTeachplanController implements CourseTeachplanControllerApi {
    @Autowired
    private CourseTeachplanServiceImpl service;

    @GetMapping("/list/{id}")
    @Override
    public TeachplanNode list(@PathVariable("id") String courseId) {
        return service.list(courseId);
    }

    @PostMapping("/add")
    @Override
    public ResponseResult add(@RequestBody Teachplan teachplan) {
        return service.add(teachplan);
    }

    @PostMapping("/savemedia")
    @Override
    public ResponseResult saveMedia(@RequestBody TeachplanMedia teachplanMedia) {
        return service.saveMedia(teachplanMedia);
    }
}
