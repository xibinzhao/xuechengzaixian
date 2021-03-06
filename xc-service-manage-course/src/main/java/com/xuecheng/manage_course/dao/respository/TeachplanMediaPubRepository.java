package com.xuecheng.manage_course.dao.respository;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub, String> {
    void deleteByCourseId(String courseId);
}
