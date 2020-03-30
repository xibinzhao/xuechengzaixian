package com.xuecheng.api.cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;


public interface CmsTemplateService {
    QueryResponseResult findAll();

    ResponseResult add(String info, String fileName, CmsTemplate cmsTemplate);

    ResponseResult delete(String templateId);

    ResponseResult update(CmsTemplate cmsTemplate, String info, String fileName);
}
