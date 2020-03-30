package com.xuecheng.learning.controller;

import com.xuecheng.api.learning.controller.CourseLearningControllerApi;
import com.xuecheng.api.learning.service.CourseLearningService;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi {
    @Autowired
    private CourseLearningService courseLearningService;

    @Override
    @GetMapping("/getmedia/{cid}/{tid}")
    public GetMediaResult getMedia(@PathVariable("cid") String courseId, @PathVariable("tid") String teachplanId) {
        return courseLearningService.getMedia(courseId, teachplanId);
    }
}
