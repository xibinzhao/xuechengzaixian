package com.xuecheng.api.course.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.ext.CourseView;

public interface CoursePerviewService {
    CourseView getById(String courseId);

    CmsPage perview(String courseId);

    CmsPageResult publish(String courseId);
}
