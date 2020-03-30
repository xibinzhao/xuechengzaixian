package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.dao.respository.TeachplanMediaPubRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class TestTeachplanMediaPub {
    @Autowired
    private TeachplanMediaPubRepository teachplanMediaPubRepository;

    @Test
    public void test() {
        TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
        teachplanMediaPub.setCourseId("33");
        teachplanMediaPub.setMediaFileOriginalName("546");
        teachplanMediaPub.setMediaUrl("5465");
        teachplanMediaPub.setTeachplanId("das");
        teachplanMediaPub.setMediaId("321");
        teachplanMediaPub.setTimestamp(new Date());
        teachplanMediaPubRepository.save(teachplanMediaPub);
    }
}
