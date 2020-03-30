package com.xuecheng.cms.controller;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.ManageCmsApplication;
import com.xuecheng.manage_cms.controller.CmsPageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class CmsPageControllerTest {
    @Autowired
    private CmsPageController controller;

    @Test
    public void testFindAll() {
        QueryResponseResult list = controller.findList(1, 2, null);
        List<CmsPage> list1 = list.getQueryResult().getList();
        System.out.println(list1);
    }

}
