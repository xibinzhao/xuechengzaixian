package com.xuecheng.api.sys.controller;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程学习的管理接口", description = "课程学习接口，提供课程学习的、删、改、查")
public interface SysDictionaryControllerApi {
    @ApiOperation("通过课程类型查询课程的相关信息")
    SysDictionary get(String type);
}
