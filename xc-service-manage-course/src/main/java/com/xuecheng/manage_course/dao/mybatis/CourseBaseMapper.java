package com.xuecheng.manage_course.dao.mybatis;

import com.xuecheng.framework.domain.course.CourseBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface CourseBaseMapper {
    @Select("select * from course_base where id = #{id}")
    CourseBase findCourseBaseById(String id);

    @Select("select course_base.*,(SELECT pic FROM course_pic WHERE courseid =course_base.id) pic  from course_base where company_id=#{id}")
    List<CourseBase> findAll(String id);

    @Select("select count(*) from course_base where company_id = #{id}")
    Integer count(String id);

    @Select("select count(*) from course_base where company_id=#{id} and name like #{name} ")
    Integer findByNameCount(@Param("id") String id, @Param("name") String name);

    @Select("select * from course_base where company_id=#{id} and name like #{name} ")
    List<CourseBase> findByName(@Param("id") String id, @Param("name") String name);
}
