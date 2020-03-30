package com.xuecheng.manage_media.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.media.service.MediaFileService;
import com.xuecheng.api.media.service.MediaUploadService;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import com.xuecheng.manage_media.utils.RabbitMqUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MediaFileServiceImpl implements MediaFileService {
    @Autowired
    private MediaFileRepository mediaFileRepository;
    @Autowired
    private RabbitMqUtils rabbitMqUtils;
    @Value("${xc-service-manage-media.mq.routingkey-media-video}")
    private String rabbitMqRoutingKey;

    @Override
    public QueryResponseResult findList(Integer page, Integer size, QueryMediaFileRequest queryMediaFileRequest) {
        page = page - 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        Example<MediaFile> example = null;
        if (queryMediaFileRequest != null) {
            MediaFile mediaFile = new MediaFile();
            ExampleMatcher exampleMatcher = ExampleMatcher.matching().
                    withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains()).
                    withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains());
            if (StringUtils.isNotEmpty(queryMediaFileRequest.getTag())) {
                mediaFile.setTag(queryMediaFileRequest.getTag());

            }
            if (StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())) {
                mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
            }
            if (StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())) {
                mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
            }
            example = Example.of(mediaFile, exampleMatcher);
        }
        Page<MediaFile> pageList = mediaFileRepository.findAll(example, pageRequest);
        QueryResult<MediaFile> result = new QueryResult<>();
        result.setList(pageList.getContent());
        result.setTotal(pageList.getTotalElements());
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, result);
        return queryResponseResult;
    }

    @Override
    public ResponseResult sendMessage(String mediaId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mediaId", mediaId);
        String message = JSON.toJSONString(map);
        rabbitMqUtils.sendMessage(rabbitMqRoutingKey, message);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
