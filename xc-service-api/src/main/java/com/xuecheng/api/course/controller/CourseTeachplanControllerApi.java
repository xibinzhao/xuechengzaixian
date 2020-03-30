package com.xuecheng.api.course.controller;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程计划管理接口", description = "课程计划管理接口，提供课程计划的增、删、改、查")
public interface CourseTeachplanControllerApi {
    @ApiOperation("通过id查询课程所有计划")
    TeachplanNode list(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult add(Teachplan teachplan);

    @ApiOperation("添加课程计划和媒体资源的关联信息")
    ResponseResult saveMedia(TeachplanMedia teachplanMedia);
}
