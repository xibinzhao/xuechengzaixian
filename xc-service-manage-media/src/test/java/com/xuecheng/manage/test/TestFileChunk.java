package com.xuecheng.manage.test;

import com.xuecheng.manage_media.ManageMediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

@SpringBootTest(classes = ManageMediaApplication.class)
@RunWith(SpringRunner.class)
public class TestFileChunk {
    @Test
    public void testChunk() throws IOException {
        File sourceFile = new File("E:\\xc_video\\hls\\lucene.mp4");
        //获取文件大小
        long length = sourceFile.length();
        //设置分块文件的大小
        long fileChunkSize = 1024 * 1024 * 1;
        //获取文件应该分为多少块
        long fileChunkNum = (long) Math.ceil(length * 1.0 / fileChunkSize);
        //设置分快文件的目录
        String chunksPath = "E:\\xc_video\\hls\\chunks\\";
        File chunksFile = new File(chunksPath);
        if (!chunksFile.exists()) {
            chunksFile.mkdirs();
        }
        byte[] b = new byte[1024];
        RandomAccessFile raf_read = new RandomAccessFile(sourceFile, "r");
        for (int i = 0; i < fileChunkNum; i++) {
            File file = new File(chunksPath + i);
            boolean newFile = file.createNewFile();
            if (newFile) { //向分块文件中写数据
                RandomAccessFile raf_write = new RandomAccessFile(file, "rw");
                int len = -1;
                while ((len = raf_read.read(b)) != -1) {
                    raf_write.write(b, 0, len);
                    if (file.length() > fileChunkSize) {
                        break;
                    }
                }
                raf_write.close();
            }
        }
        raf_read.close();
    }

    @Test
    public void testMerge() throws IOException {
        File sourceFiles = new File("E:\\xc_video\\hls\\chunks\\");
        File[] fileArray = sourceFiles.listFiles();
        File mergeFile = new File("E:\\xc_video\\hls\\lucene1.mp4");
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        //创建新的合并文件 
        mergeFile.createNewFile();
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        //指针指向文件顶端
        raf_write.seek(0);
        // 缓冲区
        byte[] b = new byte[1024];
        // 从小到大排序
        List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                    return -1;
                }
                return 1;
            }
        });
        for (File chunkFile : fileList) {
            RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "rw");
            int len = -1;
            while ((len = raf_read.read(b)) != -1) {
                raf_write.write(b, 0, len);
            }
            raf_read.close();
        }
        raf_write.close();
    }
}
