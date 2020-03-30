package com.xuecheng.test.freemarker.controller;

import com.xuecheng.test.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("test1")
    public String test1(Map<String, Object> map) {
        map.put("name", "bendan");
        Student student = new Student();
        student.setAge(18);
        student.setBirthday(new Date());
        student.setName("bendan");
        student.setMoney(3333.00f);
        Student student2 = new Student();
        student2.setAge(18);
        student2.setBirthday(new Date());
        student2.setName("bendan");
        student2.setMoney(22.00f);
        ArrayList<Student> students = new ArrayList<>();
        students.add(student2);
        students.add(student);
        HashMap<String, Student> stuMap = new HashMap<>();
        stuMap.put("stu1", student);
        stuMap.put("stu2", student2);
        map.put("students", students);
        map.put("stuMap", stuMap);
        return "test1";
    }

    @RequestMapping("banner")
    public String getBanner(Map<String, Object> map) {
        ResponseEntity<Map> res = restTemplate.getForEntity("http://localhost:31001/cms/config/findByID/5a791725dd573c3574ee333f", Map.class);
        Map body = res.getBody();
        System.out.println(body);
        map.putAll(body);
        return "index_banner";
    }
}
