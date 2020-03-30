package com.xuecheng.manage.test;

import com.xuecheng.manage_media.ManageMediaApplication;
import com.xuecheng.manage_media.utils.RabbitMqUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ManageMediaApplication.class)
@RunWith(SpringRunner.class)
public class TestMq {
    @Autowired
    private RabbitMqUtils rabbitMqUtils;

    @Test
    public void test() {
        String message = "{mediaId:'c5c75d70f382e6016d2f506d134eee11'}";
        rabbitMqUtils.sendMessage("routingkey_media_video", message);
    }
}
