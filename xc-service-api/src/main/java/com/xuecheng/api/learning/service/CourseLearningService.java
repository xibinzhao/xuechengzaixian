package com.xuecheng.api.learning.service;

import com.xuecheng.framework.domain.learning.response.GetMediaResult;

public interface CourseLearningService {
    GetMediaResult getMedia(String courseId, String teachplanId);
}
