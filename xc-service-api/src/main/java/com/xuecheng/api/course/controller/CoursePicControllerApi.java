package com.xuecheng.api.course.controller;

import com.xuecheng.framework.domain.course.response.CoursePicResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("提供课程图片的增删改查")
public interface CoursePicControllerApi {
    @ApiOperation("提供课程图片的添加")
    ResponseResult add(String courseId, String picId);

    @ApiOperation("通过courseId查询图片路径")
    CoursePicResult getByCourseId(String courseId);

    @ApiOperation("通过courseId删除图片")
    ResponseResult deleteById(String courseId);
}
