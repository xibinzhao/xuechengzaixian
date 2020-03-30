package com.xuecheng.api.search.controller;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api("提供课程的搜索")
public interface SearchControllerApi {
    @ApiOperation("课程搜索")
    QueryResponseResult list(Integer page, Integer size, CourseSearchParam courseSearchParam);

    @ApiOperation("根据课程id查询课程信息")
    Map<String, CoursePub> getAll(String courseId);

    @ApiOperation("根据课程id查询课程媒资信息")
    List<TeachplanMediaPub> findByCourseId(String courseId);

    @ApiOperation("通过课程计划id查询对应的媒资信息")
    TeachplanMediaPub getMedia(String teachplanId);
}
