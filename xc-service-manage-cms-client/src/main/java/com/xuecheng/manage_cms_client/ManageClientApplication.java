package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")
@ComponentScan("com.xuecheng.framework.exception")
@ComponentScan("com.xuecheng.manage_cms_client")
public class ManageClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageClientApplication.class, args);
    }
}
