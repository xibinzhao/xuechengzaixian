package com.xuecheng.api.cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;


public interface CmsPageService {
    QueryResponseResult findAll(int page, int size, QueryPageRequest pageRequest);

    CmsPageResult add(CmsPage cmsPage);

    CmsPageResult findById(String pageId);

    CmsPageResult updatePage(CmsPage cmsPage);

    CmsPageResult deletePage(String pageId);

    String pageHtml(String pageId);

    void post(String pageId);

    CmsPageResult save(CmsPage cmsPage);
}
