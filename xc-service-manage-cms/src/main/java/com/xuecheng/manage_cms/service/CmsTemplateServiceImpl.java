package com.xuecheng.manage_cms.service;

import com.xuecheng.api.cms.service.CmsTemplateService;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsTemplateRespsitory;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.utils.GridFsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CmsTemplateServiceImpl implements CmsTemplateService {
    @Autowired
    private CmsTemplateRespsitory templateRespsitory;
    @Autowired
    private GridFsUtils gridFsUtils;

    @Override
    public QueryResponseResult findAll() {
        List<CmsTemplate> all = templateRespsitory.findAll();
        QueryResult<CmsTemplate> templateQueryResult = new QueryResult<>();
        templateQueryResult.setTotal(all.size());
        templateQueryResult.setList(all);
        return new QueryResponseResult(CommonCode.SUCCESS, templateQueryResult);
    }

    @Override
    public ResponseResult add(String info, String fileName, CmsTemplate cmsTemplate) {
        if (info == null || fileName == null || cmsTemplate == null) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        String templateId = cmsTemplate.getTemplateId();
        if (templateId != null) {
            if (get(cmsTemplate.getTemplateId()) != null) {
                throw new CustomException(CmsCode.CMS_ADDPAGE_EXISTSNAME);
            }
        }
        String fileId = gridFsUtils.insert(info, fileName);
        cmsTemplate.setTemplateFileId(fileId);
        CmsTemplate save = templateRespsitory.save(cmsTemplate);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public ResponseResult delete(String templateId) {
        CmsTemplate cmsTemplate = get(templateId);
        if (cmsTemplate == null) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        String templateFileId = cmsTemplate.getTemplateFileId();
        gridFsUtils.delete(templateFileId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public ResponseResult deleteAll(String templateId) {
        delete(templateId);
        CmsTemplate cmsTemplate = get(templateId);
        templateRespsitory.delete(cmsTemplate);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public ResponseResult update(CmsTemplate cmsTemplate, String info, String fileName) {
        delete(cmsTemplate.getTemplateFileId());
        CmsTemplate cmsTemplate1 = get(cmsTemplate.getTemplateId());
        cmsTemplate1.setSiteId(cmsTemplate.getSiteId());
        cmsTemplate1.setTemplateName(cmsTemplate.getTemplateName());
        cmsTemplate1.setTemplateParameter(cmsTemplate1.getTemplateParameter());
        String insert = gridFsUtils.insert(info, fileName);
        cmsTemplate1.setTemplateFileId(insert);
        templateRespsitory.save(cmsTemplate1);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CmsTemplate get(String templateId) {
        Optional<CmsTemplate> byId = templateRespsitory.findById(templateId);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }
}
