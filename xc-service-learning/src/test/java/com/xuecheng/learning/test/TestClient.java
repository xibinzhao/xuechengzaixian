package com.xuecheng.learning.test;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.learning.LearningApplication;
import com.xuecheng.learning.client.SearchClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = LearningApplication.class)
@RunWith(SpringRunner.class)
public class TestClient {
    @Autowired
    private SearchClient searchClient;

    @Test
    public void test1() {
        TeachplanMediaPub media = searchClient.getMedia("40285a816e67c0ee016e67c9f5d20000");
        System.out.println(media);
    }
}
