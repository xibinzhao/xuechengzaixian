package com.xuecheng.ucenter.dao.mapper;

import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.apache.ibatis.annotations.*;

@Mapper
public interface XcUserMapper {
    @Results(id = "XcUserExt", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(property = "permissions", column = "id", many = @Many(select = "com.xuecheng.ucenter.dao.mapper.XcMenuMapper.findByUid")),
            @Result(property = "companyId", column = "id", one = @One(select = "com.xuecheng.ucenter.dao.mapper.XcCompanyMapper.findCompanyIdByUserId"))
    })
    @Select("select * from xc_user where username=#{username}")
    XcUserExt get(String username);
}
