package com.xuecheng.api.cms.controller;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //页面查询
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页 码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录 数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(Integer page, Integer size, QueryPageRequest pageRequest);

    @ApiOperation("添加页面")
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("根据id查询页面信息")
    CmsPageResult findById(String pageId);

    @ApiOperation("修改页面")
    CmsPageResult updatePage(CmsPage cmsPage);

    @ApiOperation("删除页面")
    CmsPageResult deletePage(String pageId);

    @ApiOperation("静态化且发布页面")
    void post(String pageId);

    @ApiOperation("保存页面  有则修改没有则新增")
    CmsPageResult save(CmsPage cmsPage);

}
