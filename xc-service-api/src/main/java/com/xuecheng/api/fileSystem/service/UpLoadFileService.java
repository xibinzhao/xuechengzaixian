package com.xuecheng.api.fileSystem.service;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.web.multipart.MultipartFile;

public interface UpLoadFileService {
    UploadFileResult uploadFile(MultipartFile file, String businesskey, String filetag, String metadata);
}
