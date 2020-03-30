package com.xuecheng.api.cms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = "提供页面预览功能")
public interface CmsPagePreviewControllerApi {
    @ApiOperation("提供页面预览功能")
    void preview(String pageId);
}
