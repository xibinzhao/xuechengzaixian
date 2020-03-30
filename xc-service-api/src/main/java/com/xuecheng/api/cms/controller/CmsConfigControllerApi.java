package com.xuecheng.api.cms.controller;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "cms配置信息接口", description = "cms配置管理接口，提供页面的增、删、改、查")
public interface CmsConfigControllerApi {
    @ApiOperation("通过id查询 页面配置信息")
    CmsConfig findById(String id);
}
