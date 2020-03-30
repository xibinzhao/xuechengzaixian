package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.dao.mybatis.CourseCategoryMapper;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class CategoryDaoTest {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Test
    public void test1() {
        CategoryNode all = courseCategoryMapper.findAll();
        System.out.println(all);
    }
}
