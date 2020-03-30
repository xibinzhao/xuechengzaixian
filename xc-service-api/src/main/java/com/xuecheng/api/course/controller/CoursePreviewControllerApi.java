package com.xuecheng.api.course.controller;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.ext.CourseView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("提供课程页面的预览功能")
public interface CoursePreviewControllerApi {
    @ApiOperation("获取课程详情页面预览数据")
    CourseView getById(String courseId);

    @ApiOperation("课程详情页面预览")
    CmsPage perview(String courseId);

    @ApiOperation("课程详情页面发布")
    CmsPageResult publish(String courseId);
}
