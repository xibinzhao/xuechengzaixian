package com.xuecheng.manage_media.controller;

import com.xuecheng.api.media.controller.MediaFileControllerApi;
import com.xuecheng.api.media.service.MediaFileService;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {
    @Autowired
    private MediaFileService mediaFileService;

    @Override
    @GetMapping("/list")
    public QueryResponseResult findList(Integer page, Integer size, QueryMediaFileRequest queryMediaFileRequest) {
        return mediaFileService.findList(page, size, queryMediaFileRequest);
    }

    @GetMapping("/send/{mediaId}")
    @Override
    public ResponseResult sendMessage(@PathVariable("mediaId") String mediaId) {
        return mediaFileService.sendMessage(mediaId);
    }
}
