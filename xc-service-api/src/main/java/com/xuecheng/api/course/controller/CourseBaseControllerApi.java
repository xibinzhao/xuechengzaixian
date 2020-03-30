package com.xuecheng.api.course.controller;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程管理接口", description = "课程管理接口，提供课程的增、删、改、查")
public interface CourseBaseControllerApi {
    @ApiOperation("通过课程ID查询课程基本信息")
    CourseBase findById(String courseId);

    @ApiOperation("通过课程ID查询课程基本信息")
    QueryResponseResult findAll(Integer page, Integer size, CourseListRequest courseListRequest);

    @ApiOperation("添加课程")
    AddCourseResult add(CourseBase courseBase);

    @ApiOperation("通过id修改课程信息")
    AddCourseResult update(CourseBase courseBase);

    @ApiOperation("通过courseid查询课程价格")
    CourseMarket getByCourseId(String courseId);

    @ApiOperation("添加营销方案")
    ResponseResult updateCourseMarket(CourseMarket courseMarket);
}
