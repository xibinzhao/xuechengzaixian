package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.controller.CmsConfigControllerApi;
import com.xuecheng.api.cms.service.CmsConfigService;
import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    private CmsConfigService cmsConfigService;

    @GetMapping("findByID/{id}")
    @Override
    public CmsConfig findById(@PathVariable("id") String id) {
        return cmsConfigService.findById(id);
    }
}
