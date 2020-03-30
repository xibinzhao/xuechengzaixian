package com.xuecheng.api.media.controller;

import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("提供媒体资源的增删改查")
public interface MediaFileControllerApi {
    @ApiOperation("提供查询所有的媒体资源文件信息")
    QueryResponseResult findList(Integer page, Integer size, QueryMediaFileRequest queryMediaFileRequest);

    @ApiOperation("发送消息处理文件")
    ResponseResult sendMessage(String mediaId);
}
