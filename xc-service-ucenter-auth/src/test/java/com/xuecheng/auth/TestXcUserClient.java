package com.xuecheng.auth;

import com.xuecheng.auth.client.XcUserClient;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestXcUserClient {
    @Autowired
    private XcUserClient xcUserClient;

    @Test
    public void test() {
        XcUserExt itcast = xcUserClient.getUserext("itcast");
        System.out.println(itcast);
    }
}
