package com.xuecheng.manage_course.dao;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.client.CmsTemplateClient;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class AddTemplateTest {
    @Autowired
    private CmsTemplateClient cmsTemplateClient;

    @Test
    public void test() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:/course.ftl");
        String s = IOUtils.toString(fileInputStream, "UTF-8");
        CmsTemplate cmsTemplate = new CmsTemplate();
        cmsTemplate.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsTemplate.setTemplateParameter("courseid");
        cmsTemplate.setTemplateName("测试模板  课程详情页");
        System.out.println(s);
        ResponseResult add = cmsTemplateClient.add(s, "course.ftl", cmsTemplate);
        System.out.println(add);
    }

    @Test
    public void test2() {
        QueryResponseResult all = cmsTemplateClient.findAll();
        System.out.println(all);

    }
}
