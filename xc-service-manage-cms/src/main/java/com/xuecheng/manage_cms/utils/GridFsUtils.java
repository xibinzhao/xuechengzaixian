package com.xuecheng.manage_cms.utils;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class GridFsUtils {
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    public String insert(String info, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = IOUtils.toInputStream(info, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectId store = gridFsTemplate.store(inputStream, fileName);
        String s = store.toString();
        return s;
    }

    public String checkOut(String fileId, String fileName) {
        //通过文件的files_id获取文件
        GridFSFile gridFsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //通过  gridFSBucket打开一个下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFsFile.getObjectId());
        //截取下载流
        GridFsResource gridFsResource = new GridFsResource(gridFsFile, gridFSDownloadStream);
        //读取流中的内容
        String template = null;
        try {
            template = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出打印
        return template;

    }

    public void delete(String fileId) {
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
    }
}
