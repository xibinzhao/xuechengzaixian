package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.controller.CmsPagePreviewControllerApi;
import com.xuecheng.api.cms.service.CmsPageService;
import com.xuecheng.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("/cms/preview")
@Controller
public class CmsPagePreviewController extends BaseController implements CmsPagePreviewControllerApi {
    @Autowired
    private CmsPageService cmsPageService;

    @GetMapping("/{id}")
    @Override
    public void preview(@PathVariable("id") String pageId) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(cmsPageService.pageHtml(pageId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
