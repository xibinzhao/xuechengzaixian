package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.controller.CmsTemplateControllerAPI;
import com.xuecheng.api.cms.service.CmsTemplateService;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cms/temp")
@RestController
public class CmsTempleteController implements CmsTemplateControllerAPI {
    @Autowired
    private CmsTemplateService templateService;

    @GetMapping("list")
    @Override
    public QueryResponseResult findAll() {
        return templateService.findAll();
    }

    @PostMapping("add")
    public ResponseResult add(String info, String fileName, @RequestBody CmsTemplate template) {
        return templateService.add(info, fileName, template);
    }

    @GetMapping("/delete/{id}")
    @Override
    public ResponseResult delete(@PathVariable("id") String templateId) {
        return templateService.delete(templateId);
    }

    @PostMapping("/update")
    @Override
    public ResponseResult update(@RequestBody CmsTemplate cmsTemplate, String info, String fileName) {
        return templateService.update(cmsTemplate, info, fileName);
    }
}
