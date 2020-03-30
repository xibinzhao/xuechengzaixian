package com.xuecheng.cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.manage_cms.ManageCmsApplication;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class GridFsFileTest {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Test
    public void insert() throws FileNotFoundException {
        //定义输入流
        FileInputStream fileInputStream = new FileInputStream(new File("D:/test/index_banner.ftl"));
        //存储且获取到文件的id
        ObjectId store = gridFsTemplate.store(fileInputStream, "轮播图测试005");
        //输出id
        System.out.println(store);
    }

    @Test
    public void get() throws IOException {
        //通过文件的files_id获取文件
        GridFSFile gridFsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5db7de74cb47ef32a8ece720")));
        //通过  gridFSBucket打开一个下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFsFile.getObjectId());
        //截取下载流
        GridFsResource gridFsResource = new GridFsResource(gridFsFile, gridFSDownloadStream);
        //读取流中的内容
        String template = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
        //输出打印
        System.out.println(template);
    }

    @Test
    public void test() {
        String files_id = "5db7de74cb47ef32a8ece720";
        GridFSFile gridFsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5db7de74cb47ef32a8ece720")));
        System.out.println(gridFsFile == null);
    }
}
