package com.xuecheng;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.manage_cms.ManageCmsApplication;
import com.xuecheng.manage_cms.feignClient.CourseBaseClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class TestFeign {
    @Autowired
    private CourseBaseClient courseBaseClient;

    @Test
    public void test1() {
        CourseBase byId = courseBaseClient.findById("297e7c7c62b888f00162b8a7dec20000");
        System.out.println(byId);
    }
}
