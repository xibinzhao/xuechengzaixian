package com.xuecheng;

import com.xuecheng.manage_cms.ManageCmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class TestRibbon {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {
        String serverName = "XC-SERVICE-MANAGE-COURSE";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://" + serverName + "/course/coursebase/getById/297e7c7c62b888f00162b8a7dec20000", Map.class);
        Map body = forEntity.getBody();
        System.out.println(body);
    }
}
