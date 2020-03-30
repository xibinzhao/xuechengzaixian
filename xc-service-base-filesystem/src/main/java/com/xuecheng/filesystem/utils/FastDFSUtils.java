package com.xuecheng.filesystem.utils;

import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.exception.ExceptionCast;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FastDFSUtils {
    @Value("${xuecheng.fastdfs.tracker_servers}")
    private String tracker_servers;
    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    private Integer connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    private Integer network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    private String charset;
    private TrackerServer trackerServer;
    private TrackerClient trackerClient;
    private StorageClient1 storageClient1;
    private StorageServer storageServer;

    public void init() throws IOException, MyException {
        ClientGlobal.setG_charset(charset);
        ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
        ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
        ClientGlobal.initByTrackers(tracker_servers);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = trackerClient.getStoreStorage(trackerServer);
        storageClient1 = new StorageClient1(trackerServer, storageServer);
    }

    public String saveFile(MultipartFile file) {
        try {
            init();
        } catch (Exception e) {
            ExceptionCast.cast(FileSystemCode.FS_ININTFDFS_EXCEPTION);
        }
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            String name = file.getOriginalFilename();
            String exit = name.substring(name.lastIndexOf(".") + 1);
            String s = storageClient1.upload_file1(bytes, exit, null);
            return s;
        } catch (IOException | MyException e) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        return null;
    }


}
