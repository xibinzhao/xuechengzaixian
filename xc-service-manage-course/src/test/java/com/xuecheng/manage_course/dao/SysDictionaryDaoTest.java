package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.dao.respository.SysDictionaryRepository;
import com.xuecheng.manage_course.service.SysDictionaryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class SysDictionaryDaoTest {
    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    @Test
    public void test1() {
        SysDictionary byDType = sysDictionaryRepository.findByDType("200");
        System.out.println(byDType);
    }
}
