package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.dao.respository.CourseTeachplanRepository;
import com.xuecheng.manage_course.dao.respository.TeachplanMediaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class TestTeachplanMedia {
    @Autowired
    private TeachplanMediaRepository teachplanMediaRepository;
    @Autowired
    private CourseTeachplanRepository teachplanRepository;

    @Test
    public void test() {
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setTeachplanId("333");
        teachplanMedia.setMediaUrl("dhhh");
        teachplanMedia.setMediaFileOriginalName("dasd");
        teachplanMedia.setCourseId("eqw");
        teachplanMedia.setMediaId("56456");
        teachplanMediaRepository.save(teachplanMedia);
    }

    @Test
    public void test2() {
        teachplanRepository.findById("40285a816e351944016e352837e40003");
    }
}
