package com.xuecheng.ucenter.service;

import com.xuecheng.api.ucenter.service.XcUserService;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.mapper.XcUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XcUserServiceImpl implements XcUserService {
    @Autowired
    private XcUserMapper xcUserMapper;

    @Override
    public XcUserExt getUserext(String username) {
        return xcUserMapper.get(username);
    }
}
