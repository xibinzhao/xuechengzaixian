package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.controller.CmsPageControllerApi;
import com.xuecheng.api.cms.service.CmsPageService;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.utils.RabbitMqUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/cms/page")
@RestController
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    private CmsPageService cmsPageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") Integer page, @PathVariable("size") Integer size, QueryPageRequest pageRequest) {
        return cmsPageService.findAll(page, size, pageRequest);
    }
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    @GetMapping("/findById/{pageId}")
    public CmsPageResult findById(@PathVariable("pageId") String pageId) {
        return cmsPageService.findById(pageId);
    }

    @PostMapping("update")
    public CmsPageResult updatePage(@RequestBody CmsPage cmsPage) {
        return cmsPageService.updatePage(cmsPage);
    }

    @DeleteMapping("delete/{pageId}")
    public CmsPageResult deletePage(@PathVariable("pageId") String pageId) {
        return cmsPageService.deletePage(pageId);
    }
    @GetMapping("/release/{id}")
    @Override
    public void post(@PathVariable("id") String pageId) {
        cmsPageService.post(pageId);
    }
    @PostMapping("/save")
    @Override
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return cmsPageService.save(cmsPage);
    }
}
