package com.xuecheng.api.course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

public interface CourseBaseService {
    CourseBase findById(String courseId);

    QueryResponseResult findAll(Integer page, Integer size, CourseListRequest courseListRequest);

    AddCourseResult add(CourseBase courseBase);

    AddCourseResult update(CourseBase courseBase);

    CourseMarket getByCourseId(String courseId);

    ResponseResult updateCourseMarket(CourseMarket courseMarket);
}
