package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    CmsPage findByPageName(String bendan);

    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String SiteId, String pageWebPath);

    CmsPage findByDataUrlAndTemplateId(String dataUrl, String templateId);
}
