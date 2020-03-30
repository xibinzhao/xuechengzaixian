package com.xuecheng;

import com.xuecheng.api.cms.service.CmsTemplateService;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.ManageCmsApplication;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class AddTemplateTest {
    @Autowired
    private CmsTemplateService service;

    @Test
    public void test1() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:/course.ftl");
        String s = IOUtils.toString(fileInputStream, "UTF-8");
        CmsTemplate cmsTemplate = new CmsTemplate();
        cmsTemplate.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsTemplate.setTemplateParameter("courseid");
        cmsTemplate.setTemplateName("测试模板  课程详情页");
        service.add(s, "course.ftl", cmsTemplate);
    }

    @Test
    public void test2() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("E:/templateFtl/teacher_info_template03.ftl");
        String s = IOUtils.toString(fileInputStream, "UTF-8");
        CmsTemplate cmsTemplate = new CmsTemplate();
        cmsTemplate.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsTemplate.setTemplateParameter("courseid");
        cmsTemplate.setTemplateName("教师信息模板");
        service.add(s, "teacher.ftl", cmsTemplate);
    }

    @Test
    public void test3() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("E:/templateFtl/company_info_template2.ftl");
        String s = IOUtils.toString(fileInputStream, "UTF-8");
        CmsTemplate cmsTemplate = new CmsTemplate();
        cmsTemplate.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsTemplate.setTemplateParameter("courseid");
        cmsTemplate.setTemplateName("公司信息模板");
        service.add(s, "company.ftl", cmsTemplate);
    }


}
