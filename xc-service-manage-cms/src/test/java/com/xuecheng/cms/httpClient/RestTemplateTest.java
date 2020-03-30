package com.xuecheng.cms.httpClient;

import com.xuecheng.manage_cms.ManageCmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class RestTemplateTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testHttpClient() {
        ResponseEntity<Map> res = restTemplate.getForEntity("http://localhost:31001/cms/config/findByID/5a791725dd573c3574ee333f", Map.class);
        Map body = res.getBody();
        List model = (List) body.get("model");
        System.out.println(model);
        for (Object map : model) {
            Map map2 = (Map) map;
            Object value = map2.get("value");
            System.out.println(value);
        }
        System.out.println(body);
    }
}
