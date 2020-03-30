package com.xuecheng.manage_course.dao;

import com.xuecheng.api.course.service.CoursePerviewService;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class CmsPageClientTest {
    @Autowired
    private CmsPageClient cmsPageClient;
    @Autowired
    private CoursePerviewService coursePerviewService;

    @Test
    public void test() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setDataUrl("http://localhost:31200/course/prview/getModel/" + "297e7c7c62b888f00162b8a7dec20000");
        cmsPage.setPageName("297e7c7c62b888f00162b8a7dec20000.html");
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setTemplateId("5dc39091cb47ef8414fb64b1");
        cmsPage.setPageWebPath("/course/");
        cmsPage.setPageAliase("test");
        cmsPage.setPageCreateTime(new Date());
        cmsPage.setPagePhysicalPath("E:/xcEduUi/course/detail");
        cmsPage.setPageType("0");
        CmsPageResult save = cmsPageClient.save(cmsPage);
        System.out.println(save);
    }

    @Test
    public void test2() {
        CmsPageResult publish = coursePerviewService.publish("40285a816e342c72016e344bad940003");
        System.out.println(publish);
    }
}
