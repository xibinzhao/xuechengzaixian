package com.xuecheng.api.course.controller;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程类型管理接口", description = "提供课程类型的、删、改、查")
public interface CourseCategoryControllerAPI {
    @ApiOperation("查询所有的类型")
    CategoryNode findAll();
}
