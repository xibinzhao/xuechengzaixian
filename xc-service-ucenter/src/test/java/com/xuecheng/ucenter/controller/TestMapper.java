package com.xuecheng.ucenter.controller;

import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.mapper.XcUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMapper {
    @Autowired
    private XcUserMapper xcUserMapper;

    @Test
    public void test() {
        XcUserExt itcast = xcUserMapper.get("itcast");
        System.out.println(itcast);
    }
}
