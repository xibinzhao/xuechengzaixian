package com.xuecheng.api.fileSystem.controller;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api("提供上传文件的接口")
public interface UpLoadFileControllerApi {
    @ApiOperation("上传文件")
    UploadFileResult uploadFile(MultipartFile file, String businesskey, String filetag, String metadata);
}
