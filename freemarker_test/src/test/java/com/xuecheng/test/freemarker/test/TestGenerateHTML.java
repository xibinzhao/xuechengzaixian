package com.xuecheng.test.freemarker.test;

import com.xuecheng.test.freemarker.FreemarkerTestApplication;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;
import sun.nio.ch.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = FreemarkerTestApplication.class)
@RunWith(SpringRunner.class)
public class TestGenerateHTML {
    @Autowired
    private RestTemplate restTemplate;

    //加载 模板 文件 静态化
    @Test
    public void test() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板路径
        String classpath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates/"));
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //加载模板
        Template template = configuration.getTemplate("course.ftl");
        //数据模型
        // HashMap<String, Object> map = new HashMap<>();
        //map.put("name","tulu");
        ResponseEntity<Map> res = restTemplate.getForEntity("http://localhost:31200/course/prview/getModel/297e7c7c62b888f00162b8a7dec20000", Map.class);
        Map map = res.getBody();
        //静态化
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        //静态化内容
        System.out.println(html);
        //输出静态化内容
        InputStream inputStream = IOUtils.toInputStream(html);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:/xc_html/static/course/detail/course.html"));
        int copy = IOUtils.copy(inputStream, fileOutputStream);
        System.out.println(copy);
    }

    //字符串模板静态化
    @Test
    public void stringHtml() throws IOException, TemplateException {
        String templateString = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf‐8\">\n" +
                "    <title>Hello World!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<#--Hello ${name}!-->\n" +
                "Hello ${name}\n" +
                "<br/>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //加载模板
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateString);
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = configuration.getTemplate("template", "utf-8");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "goudan");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(html);
        InputStream inputStream = IOUtils.toInputStream(html);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:/test2.html"));
        IOUtils.copy(inputStream, fileOutputStream);
    }

    @Test
    public void html() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        String classPath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(classPath + "/templates/"));
        Template template = configuration.getTemplate("index_banner.ftl");
        HashMap<String, Object> map = new HashMap<>();
        ResponseEntity<Map> res = restTemplate.getForEntity("http://localhost:31001/cms/config/findByID/5a791725dd573c3574ee333f", Map.class);
        Map body = res.getBody();
        map.putAll(body);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(html);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:/index_banner.html"));
        InputStream inputStream = IOUtils.toInputStream(html);
        IOUtils.copy(inputStream, fileOutputStream);
    }
}
