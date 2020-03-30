package com.xuecheng.manage_course.service;

import com.xuecheng.api.course.service.CourseCategoryService;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.mybatis.CourseCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public CategoryNode findAll() {
        return courseCategoryMapper.findAll();
    }
}
