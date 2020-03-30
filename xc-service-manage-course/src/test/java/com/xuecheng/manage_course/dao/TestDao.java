package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.dao.mybatis.CourseBaseMapper;
import com.xuecheng.manage_course.dao.mybatis.CourseTeachplanMapper;
import com.xuecheng.manage_course.dao.respository.CourseBaseRepository;
import com.xuecheng.manage_course.dao.respository.CourseTeachplanRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseBaseMapper courseMapper;
    @Autowired
    private CourseTeachplanMapper teachplanMapper;
    @Autowired
    private CourseTeachplanRepository courseTeachplanRepository;

    @Test
    public void testCourseBaseRepository() {
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816240d276016240f7e5000002");
        if (optional.isPresent()) {
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

    @Test
    public void testCourseMapper() {
        CourseBase courseBase = courseMapper.findCourseBaseById("402885816240d276016240f7e5000002");
        System.out.println(courseBase);
    }

    @Test
    public void test() {
        TeachplanNode byId = teachplanMapper.findById("1");
        List<TeachplanNode> children = byId.getChildren();
        System.out.println(byId);
        for (TeachplanNode teachplanNode : children) {
            String nn = "-";
            System.out.println(nn + "" + teachplanNode);
            List<TeachplanNode> children1 = teachplanNode.getChildren();
            for (TeachplanNode teachplanNode1 : children1) {
                String nn1 = "--";
                System.out.println(nn1 + "" + teachplanNode1);
            }

        }
    }

    @Test
    public void test1() {
        List<TeachplanNode> byParentId = teachplanMapper.findByParentId("1");
    }

    @Test
    public void test2() {
        Teachplan byGradeAndCourseId = courseTeachplanRepository.findByGradeAndCourseid("1", "4028e581617f945f01617f9dabc40000");
        System.out.println(byGradeAndCourseId);
    }
}
