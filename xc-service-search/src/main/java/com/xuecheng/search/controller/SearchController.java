package com.xuecheng.search.controller;

import com.xuecheng.api.search.controller.SearchControllerApi;
import com.xuecheng.api.search.service.SearchService;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search/course")
public class SearchController implements SearchControllerApi {
    @Autowired
    private SearchService searchService;

    @GetMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult list(@PathVariable("page") Integer page, @PathVariable("size") Integer size, CourseSearchParam courseSearchParam) {
        return searchService.list(page, size, courseSearchParam);
    }

    @GetMapping("/getall/{id}")
    @Override
    public Map<String, CoursePub> getAll(@PathVariable("id") String courseId) {
        return searchService.getAll(courseId);
    }

    @GetMapping("/getmediaByCourseId/{id}")
    @Override
    public List<TeachplanMediaPub> findByCourseId(@PathVariable("id") String courseId) {
        return searchService.findByCourseId(courseId);
    }

    @GetMapping("/getMedia/{id}")
    @Override
    public TeachplanMediaPub getMedia(@PathVariable("id") String teachplanId) {
        return searchService.getMedia(teachplanId);
    }
}
