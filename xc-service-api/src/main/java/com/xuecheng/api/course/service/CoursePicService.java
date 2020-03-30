package com.xuecheng.api.course.service;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.response.CoursePicResult;
import com.xuecheng.framework.model.response.ResponseResult;

public interface CoursePicService {
    ResponseResult add(String courseId, String picId);

    CoursePicResult getByCourseId(String courseId);

    ResponseResult deleteById(String courseId);

    public CoursePic getCoursePic(String courseId);
}
