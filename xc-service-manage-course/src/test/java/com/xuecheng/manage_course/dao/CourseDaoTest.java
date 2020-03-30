package com.xuecheng.manage_course.dao;

import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.dao.mybatis.CourseBaseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class CourseDaoTest {
    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Test
    public void test1() {
        Integer count = courseBaseMapper.count("2");
        System.out.println(count);
    }
}
