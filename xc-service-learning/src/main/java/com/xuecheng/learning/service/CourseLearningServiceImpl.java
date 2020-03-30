package com.xuecheng.learning.service;

import com.xuecheng.api.learning.service.CourseLearningService;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.framework.domain.learning.response.LearningCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.learning.client.SearchClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseLearningServiceImpl implements CourseLearningService {
    @Autowired
    private SearchClient searchClient;

    @Override
    public GetMediaResult getMedia(String courseId, String teachplanId) {
        TeachplanMediaPub media = searchClient.getMedia(teachplanId);
        if (media == null || StringUtils.isEmpty(media.getMediaUrl())) {
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        return new GetMediaResult(CommonCode.SUCCESS, media.getMediaUrl());
    }
}
