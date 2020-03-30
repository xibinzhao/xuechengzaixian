package com.xuecheng.api.media.service;

import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

public interface MediaFileService {
    QueryResponseResult findList(Integer page, Integer size, QueryMediaFileRequest queryMediaFileRequest);

    ResponseResult sendMessage(String mediaId);
}
