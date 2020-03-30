package com.xuecheng.testJson;

import com.alibaba.fastjson.JSON;
import com.xuecheng.manage_cms_client.ManageClientApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest(classes = ManageClientApplication.class)
@RunWith(SpringRunner.class)
public class TestJson {
    @Test
    public void run1() {
        String message = "{pageId:'5db689a0cb47ef264005b10f'}";
        Map map = JSON.parseObject(message, Map.class);
        System.out.println(map.get("pageId"));
    }
}
