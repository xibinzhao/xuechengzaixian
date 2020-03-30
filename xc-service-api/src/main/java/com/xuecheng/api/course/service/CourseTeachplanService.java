package com.xuecheng.api.course.service;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;

public interface CourseTeachplanService {
    TeachplanNode list(String courseId);

    ResponseResult add(Teachplan teachplan);

    ResponseResult saveMedia(TeachplanMedia teachplanMedia);
}
