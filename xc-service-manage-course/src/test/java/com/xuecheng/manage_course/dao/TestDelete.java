package com.xuecheng.manage_course.dao;

import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.service.CoursePerviewServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class TestDelete {
    @Autowired
    private CoursePerviewServiceImpl coursePerviewService;

    @Test
    public void test() {
        coursePerviewService.saveTeachMediaPub("4028e581617f945f01617f9dabc40000");
    }
}
