package com.xuecheng.api.media.controller;

import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api("提供媒体资源的上传接口")
public interface MediaUploadControllerApi {
    //检查媒体资源是否存在 和创建媒体资源所需要的文件夹
    @ApiOperation("提供媒体资源的注册")
    ResponseResult register(String fileMd5,
                            String fileName,
                            Long fileSize,
                            String mimetype,
                            String fileExt);

    @ApiOperation("块文件上传前的检查是否存在")
    CheckChunkResult checkchunk(String fileMd5,
                                Integer chunk,
                                Integer chunkSize);

    @ApiOperation("上传分块")
    ResponseResult uploadchunk(String fileMd5,
                               Integer chunk,
                               MultipartFile file);

    @ApiOperation("合并块文件与存入数据库信息")
    ResponseResult mergechunks(String fileMd5,
                               String fileName,
                               Long fileSize,
                               String mimetype,
                               String fileExt);

}
