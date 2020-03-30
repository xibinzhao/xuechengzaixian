package com.xuecheng.learning.dao.repository;

import com.xuecheng.framework.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XcLearningCourseRepository extends JpaRepository<XcLearningCourse, String> {
    XcLearningCourse findByCourseIdAndUserId(String courseId, String userId);
}
