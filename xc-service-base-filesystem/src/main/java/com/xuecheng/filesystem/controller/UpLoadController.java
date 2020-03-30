package com.xuecheng.filesystem.controller;

import com.xuecheng.api.fileSystem.controller.UpLoadFileControllerApi;
import com.xuecheng.api.fileSystem.service.UpLoadFileService;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/filesystem")
public class UpLoadController implements UpLoadFileControllerApi {
    @Autowired
    private UpLoadFileService service;

    @Override
    @PostMapping("upload")
    public UploadFileResult uploadFile(MultipartFile file, String businesskey, String filetag, String metadata) {
        return service.uploadFile(file, businesskey, filetag, metadata);
    }

}
