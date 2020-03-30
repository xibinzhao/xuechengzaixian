package com.xuecheng.cms;

import com.xuecheng.api.cms.service.CmsPageService;
import com.xuecheng.manage_cms.ManageCmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class LoderHtmlTest {
    @Autowired
    private CmsPageService cmsPageService;

    @Test
    public void test() {
        String s = cmsPageService.pageHtml("5db689a0cb47ef264005b10f");
        System.out.println(s);
    }
}
