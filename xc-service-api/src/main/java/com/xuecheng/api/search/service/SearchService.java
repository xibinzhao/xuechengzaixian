package com.xuecheng.api.search.service;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;

import java.util.List;
import java.util.Map;

public interface SearchService {
    QueryResponseResult list(Integer page, Integer size, CourseSearchParam courseSearchParam);

    Map<String, CoursePub> getAll(String courseId);

    List<TeachplanMediaPub> findByCourseId(String courseId);

    TeachplanMediaPub getMedia(String teachplanId);
}
