package com.xuecheng.api.cms.controller;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "模板查询接口")
public interface CmsTemplateControllerAPI {
    @ApiOperation("查询所有模板信息")
    QueryResponseResult findAll();

    @ApiOperation("添加模板对象")
    ResponseResult add(String info, String fileName, CmsTemplate template);

    @ApiOperation("删除模板对象")
    ResponseResult delete(String templateId);

    @ApiOperation("更新模板信息")
    ResponseResult update(CmsTemplate cmsTemplate, String info, String fileName);
}
