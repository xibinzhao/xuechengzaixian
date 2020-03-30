package com.xuecheng.api.learning.controller;

import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("获取课程对应的媒体资源信息")
public interface CourseLearningControllerApi {
    @ApiOperation("通过课程计划id查询媒体信息")
    GetMediaResult getMedia(String courseId, String teachplanId);
}
