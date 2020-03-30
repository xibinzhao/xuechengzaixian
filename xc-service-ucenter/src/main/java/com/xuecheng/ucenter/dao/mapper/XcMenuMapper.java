package com.xuecheng.ucenter.dao.mapper;

import com.xuecheng.framework.domain.ucenter.XcMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface XcMenuMapper {
    @Results(id = "xcMenu", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "p_id", property = "pId"),
            @Result(column = "is_menu", property = "isMenu"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "menu_name", property = "menuName")
    })
    @Select("select * from xc_menu where id in (SELECT menu_id FROM xc_permission WHERE role_id = (SELECT role_id FROM xc_user_role WHERE user_id= (SELECT id FROM xc_user WHERE id =#{id})));")
    List<XcMenu> findByUid(String uid);
}
