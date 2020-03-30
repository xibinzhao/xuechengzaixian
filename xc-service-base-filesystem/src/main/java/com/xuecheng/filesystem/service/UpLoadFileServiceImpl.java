package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.fileSystem.service.UpLoadFileService;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.filesystem.utils.FastDFSUtils;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UpLoadFileServiceImpl implements UpLoadFileService {
    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Autowired
    private FastDFSUtils fastDFSUtils;

    @Override
    public UploadFileResult uploadFile(MultipartFile file, String businesskey, String filetag, String metadata) {
        if (file == null) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        String fileId = fastDFSUtils.saveFile(file);
        FileSystem fileSystem = saveFileSystem(fileId, file, businesskey, filetag, metadata);
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }

    private FileSystem saveFileSystem(String fileId, MultipartFile multipartFile, String businesskey, String filetag, String metadata) {
        FileSystem fileSystem = new FileSystem();
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFilePath(fileId);
        fileSystem.setFileType(multipartFile.getContentType());
        fileSystem.setFileId(fileId);
        fileSystem.setFileSize(multipartFile.getSize());
        if (!StringUtils.isEmpty(metadata)) {
            try {
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileSystem save = fileSystemRepository.save(fileSystem);
        return save;
    }

}
