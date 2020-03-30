package com.xuecheng.ucenter.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface XcCompanyMapper {
    @Select("select id from xc_company where id =(select company_id from xc_company_user where user_id=#{id})")
    String findCompanyIdByUserId(String id);
}
