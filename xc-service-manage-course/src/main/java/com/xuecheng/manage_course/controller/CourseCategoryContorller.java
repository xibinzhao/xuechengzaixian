package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.controller.CourseCategoryControllerAPI;
import com.xuecheng.api.course.service.CourseCategoryService;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course/category")
public class CourseCategoryContorller implements CourseCategoryControllerAPI {
    @Autowired
    private CourseCategoryService courseCategoryService;

    @GetMapping("/list")
    @Override
    public CategoryNode findAll() {
        return courseCategoryService.findAll();
    }
}
