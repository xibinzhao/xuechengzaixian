package com.xuecheng.api.cms.controller;


import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "站点查询接口")
public interface CmsSiteControllerAPI {
    @ApiOperation("查询所有站点信息")
    QueryResponseResult findAll();
}
