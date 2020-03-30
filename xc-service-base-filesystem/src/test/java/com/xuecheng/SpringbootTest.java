package com.xuecheng;

import com.xuecheng.filesystem.FileSystemApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = FileSystemApplication.class)
@RunWith(SpringRunner.class)
public class SpringbootTest {
    @Value("xuecheng.fastdfs.tracker_servers")
    private String tracker_servers;
    @Value("xuecheng.fastdfs.connect_timeout_in_seconds")
    private String connect_timeout_in_seconds;
    @Value("xuecheng.fastdfs.network_timeout_in_seconds")
    private String network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    private String charset;

    @Test
    public void print() {
        System.out.println(tracker_servers);
        System.out.println(connect_timeout_in_seconds);
        System.out.println(network_timeout_in_seconds);
        System.out.println(charset);
    }

}
