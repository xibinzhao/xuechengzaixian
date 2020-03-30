package com.xuecheng.manage_course.dao.respository;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator.
 */
public interface CourseTeachplanRepository extends JpaRepository<Teachplan, String> {
    Teachplan findByGradeAndCourseid(String grade, String courseId);

    Teachplan findByParentid(String parentId);
}
