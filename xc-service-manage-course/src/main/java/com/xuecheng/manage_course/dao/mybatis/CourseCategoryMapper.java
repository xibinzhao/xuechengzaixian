package com.xuecheng.manage_course.dao.mybatis;

import com.xuecheng.framework.domain.course.Category;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface CourseCategoryMapper {
    @Select("select * from category where id = '1'")
    @Results(id = "CategoryNode", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "label", property = "label"),
            @Result(column = "parentid", property = "parentid"),
            @Result(column = "isshow", property = "isshow"),
            @Result(column = "orderby", property = "orderby"),
            @Result(column = "isleaf", property = "isleaf"),
            @Result(column = "id", property = "children", many = @Many(select = "com.xuecheng.manage_course.dao.mybatis.CourseCategoryMapper.findByParentId", fetchType = FetchType.EAGER))
    })
    CategoryNode findAll();

    @Results(id = "CategoryNode1", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "label", property = "label"),
            @Result(column = "parentid", property = "parentid"),
            @Result(column = "isshow", property = "isshow"),
            @Result(column = "orderby", property = "orderby"),
            @Result(column = "isleaf", property = "isleaf"),
            @Result(column = "id", property = "children", many = @Many(select = "com.xuecheng.manage_course.dao.mybatis.CourseCategoryMapper.findByParentId1", fetchType = FetchType.EAGER))
    })
    @Select("select * from category where parentid=#{id}")
    List<CategoryNode> findByParentId(String parentId);

    @Select("select * from category where parentid=#{id}")
    List<Category> findByParentId1(String parentId);
}
