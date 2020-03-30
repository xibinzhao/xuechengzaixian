package com.xuecheng.test.fastDfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFastDFS {
    //测试上传
    @Test
    public void upload() {
        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //创建tracker客户端
            TrackerClient trackerClient = new TrackerClient();
            //获取tracker连接
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage连接
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建客户端
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            //上传
            String fileId = storageClient1.upload_file1("d://not.png", "png", null);
            System.out.println(fileId);
            //group1/M00/00/00/wKjahF3A5piAang2ABmrN7tITJY353.png
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试下载
    @Test
    public void dowmload() throws Exception {
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        //创建tracker客户端
        TrackerClient trackerClient = new TrackerClient();
        //获取tracker连接
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storage连接
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        //创建客户端
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
        String fileId = "group1/M00/00/00/wKjahF3AzWCACVKaAA-qUvpt7QE070_big.png";
        byte[] bytes = storageClient1.download_file1(fileId);

        FileOutputStream fileOutputStream = new FileOutputStream("d://not1.png");
        fileOutputStream.write(bytes);
    }
}
