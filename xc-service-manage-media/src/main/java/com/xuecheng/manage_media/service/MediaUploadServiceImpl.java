package com.xuecheng.manage_media.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.media.service.MediaUploadService;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.domain.media.response.MediaCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import com.xuecheng.manage_media.utils.RabbitMqUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.*;
import java.util.*;

@Service
public class MediaUploadServiceImpl implements MediaUploadService {
    @Value("${xc-service-manage-media.upload-location}")
    private String fileRootPath;
    @Value("${xc-service-manage-media.mq.routingkey-media-video}")
    private String rabbitMqRoutingKey;
    @Autowired
    private RabbitMqUtils rabbitMqUtils;
    @Autowired
    private MediaFileRepository mediaFileRepository;

    /*** 根据文件md5得到文件路径
     *  规则：
     *  一级目录：md5的第一个字符 *
     * 二级目录：md5的第二个字符 * 三级目录：md5
     * 文件名：md5+文件扩展名
     * @param fileMd5 文件md5值
     *  @param fileExt 文件扩展名
     * @return 文件路径
     * */
    @Override
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        String folderPath = this.getFolderPath(fileMd5);
        String filePath = this.getFilePath(fileMd5, fileExt);
        String chunkFolderPath = this.getChunkFolderPath(fileMd5);
        //1检查磁盘文件是否存在
        File file = new File(filePath);
        boolean exists = file.exists();
        //2.检查数据库是否存在数据信息
        Optional<MediaFile> op = mediaFileRepository.findById(fileMd5);
        if (op.isPresent() && exists) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
        } else {
            File folderFile = new File(folderPath);
            if (!folderFile.exists()) {
                folderFile.mkdirs();
            }
            File chunkFolderFile = new File(chunkFolderPath);
            if (!chunkFolderFile.exists()) {
                chunkFolderFile.mkdirs();
            }
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //得到文件所在路径
    private String getFilePath(String fileMd5, String fileExt) {
        return fileRootPath + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + fileMd5 + "." + fileExt;
    }

    //得到文件上传的路径
    private String getFolderPath(String fileMd5) {
        return fileRootPath + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/";
    }

    private String getChunkFolderPath(String fileMd5) {
        return fileRootPath + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + "chunk/";
    }

    private String getPath(String fileMd5) {
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/";
    }

    @Override
    public CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {
        String chunkFolderPath = this.getChunkFolderPath(fileMd5);
        File file = new File(chunkFolderPath + chunk);
        if (file.exists()) {
            return new CheckChunkResult(CommonCode.SUCCESS, true);
        }
        return new CheckChunkResult(CommonCode.SUCCESS, false);
    }

    @Override
    public ResponseResult uploadchunk(String fileMd5, Integer chunk, MultipartFile file) {
        if (file == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String chunkFolderPath = this.getChunkFolderPath(fileMd5);
        File chunkFile = new File(chunkFolderPath + chunk);
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = file.getInputStream();
            fileOutputStream = new FileOutputStream(chunkFile);
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        String chunkFolderPath = this.getChunkFolderPath(fileMd5);
        String filePath = this.getFilePath(fileMd5, fileExt);
        File chunkFolderFile = new File(chunkFolderPath);
        File file = new File(filePath);
        File[] files = chunkFolderFile.listFiles();
        List<File> fileList = Arrays.asList(files);
        File mergeFile = this.mergeFile(fileList, file);
        boolean b = this.checkFileMd5(mergeFile, fileMd5);
        if (!b) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setFileName(fileMd5 + "." + fileExt);
        mediaFile.setFileOriginalName(fileName);
        mediaFile.setFileSize(fileSize);
        String path = this.getPath(fileMd5);
        mediaFile.setFilePath(path);
        mediaFile.setFileType(fileExt);
        mediaFile.setUploadTime(new Date());
        mediaFile.setMimeType(mimetype);
        mediaFile.setFileStatus("301002");
        MediaFile save = mediaFileRepository.save(mediaFile);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mediaId", mediaFile.getFileId());
        String message = JSON.toJSONString(map);
        rabbitMqUtils.sendMessage(rabbitMqRoutingKey, message);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private File mergeFile(List<File> fileList, File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            RandomAccessFile raf_write = new RandomAccessFile(file, "rw");
            byte[] b = new byte[1024];
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
                RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "r");
                int len = -1;
                while ((len = raf_read.read(b)) != -1) {
                    raf_write.write(b, 0, len);
                }
                raf_read.close();
            }
            raf_write.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    private boolean checkFileMd5(File megerFile, String md5) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(megerFile);
            String newMd5 = DigestUtils.md5Hex(fileInputStream);
            if (newMd5.equalsIgnoreCase(md5)) {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
