package com.xuecheng.manage_course.dao.mybatis;

import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface CourseTeachplanMapper {
    @Select("select * from teachplan where id=#{id}")
    @Results(id = "findTree", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "pname", property = "pname"),
            @Result(property = "mediaFileOriginalName", column = "id", one = @One(select = "com.xuecheng.manage_course.dao.mybatis.CourseTeachplanMapper.findByTeachplanId", fetchType = FetchType.EAGER)),
            @Result(property = "children", column = "id", many = @Many(select = "com.xuecheng.manage_course.dao.mybatis.CourseTeachplanMapper.findByParentId", fetchType = FetchType.EAGER))
    })
    TeachplanNode findById(String id);

    @Select("select * from teachplan where parentId=#{parentId}")
    @ResultMap("findTree")
    List<TeachplanNode> findByParentId(String parentId);

    @Select("select * from teachplan where courseId=#{courseId} and grade=1")
    @ResultMap("findTree")
    TeachplanNode findByCourseIdAndGrade(String courseId);

    @Select("select media_fileoriginalname from teachplan_media where teachplan_id= #{teachplanId}")
    String findByTeachplanId(String teachplanId);
}
