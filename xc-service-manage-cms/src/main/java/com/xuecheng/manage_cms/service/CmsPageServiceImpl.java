package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.api.cms.service.CmsPageService;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRespsitory;
import com.xuecheng.manage_cms.utils.GridFsUtils;
import com.xuecheng.manage_cms.utils.RabbitMqUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class CmsPageServiceImpl implements CmsPageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CmsTemplateRespsitory cmsTemplateRespsitory;
    @Autowired
    private GridFsUtils gridFsUtils;
    @Autowired
    private RabbitMqUtils rabbitMqUtils;

    @Override
    public QueryResponseResult findAll(int page, int size, QueryPageRequest pageRequest) {
        Example<CmsPage> example = null;
        if (pageRequest != null) {
            CmsPage cmsPage = new CmsPage();
            ExampleMatcher exampleMatcher = ExampleMatcher.matching();
            exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                    .withMatcher("pageWebPath", ExampleMatcher.GenericPropertyMatchers.contains());
            example = Example.of(cmsPage, exampleMatcher);
            if (!StringUtils.isEmpty(pageRequest.getPageAliase())) {
                cmsPage.setPageAliase(pageRequest.getPageAliase());
            }
            if (!StringUtils.isEmpty(pageRequest.getSiteId())) {
                cmsPage.setSiteId(pageRequest.getSiteId());
            }
            if (!StringUtils.isEmpty(pageRequest.getTemplateId())) {
                cmsPage.setTemplateId(pageRequest.getTemplateId());
            }
        }
        page = page - 1;
        PageRequest pageAble = PageRequest.of(page, size);
        Page<CmsPage> pages = cmsPageRepository.findAll(example, pageAble);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setTotal(pages.getTotalElements());
        cmsPageQueryResult.setList(pages.getContent());
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    @Override
  /*  public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage==null){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CmsPageResult cmsPageResult = null;
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1==null){
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }else{
            cmsPageResult = new CmsPageResult(CommonCode.FAIL,cmsPage);
        }

        return cmsPageResult;
    }*/
    public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        CmsPageResult cmsPageResult = null;
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        return cmsPageResult;
    }

    @Override
    public CmsPageResult findById(String pageId) {
        Optional<CmsPage> cms = cmsPageRepository.findById(pageId);
        CmsPage cmsPage = null;
        if (cms.isPresent()) {
            cmsPage = cms.get();
        }
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    @Override
    public CmsPageResult updatePage(CmsPage cmsPage) {
        Optional<CmsPage> op = cmsPageRepository.findById(cmsPage.getPageId());
        CmsPage cmsPage1 = null;
        if (op.isPresent()) {
            cmsPage1 = op.get();
            cmsPage1.setTemplateId(cmsPage.getTemplateId());
            cmsPage1.setSiteId(cmsPage.getSiteId());
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
            cmsPage1.setPageCreateTime(cmsPage.getPageCreateTime());
            cmsPage1.setDataUrl(cmsPage.getDataUrl());
            cmsPage1.setPageHtml(cmsPage.getPageHtml());
            cmsPage1.setHtmlFileId(cmsPage1.getHtmlFileId());
            CmsPage save = cmsPageRepository.save(cmsPage1);
            if (save == null) {
                return new CmsPageResult(CommonCode.FAIL, null);
            }
        }
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    @Override
    public CmsPageResult deletePage(String pageId) {
        Optional<CmsPage> op = cmsPageRepository.findById(pageId);
        if (op.isPresent()) {
            cmsPageRepository.deleteById(pageId);
            return new CmsPageResult(CommonCode.SUCCESS, null);
        }
        ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    @Override
    public String pageHtml(String pageId) {
        CmsPage cmsPage = get(pageId);
        String dataUrl = cmsPage.getDataUrl();
        Map model = getModel(dataUrl);
        String template = getTemplate(cmsPage.getTemplateId());
        String html = loderHtml(template, model);
        return html;
    }

    //静态化页面  返回页面字符串
    public String loderHtml(String template, Map map) {
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", template);
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            Template template1 = configuration.getTemplate("template");
            String s = FreeMarkerTemplateUtils.processTemplateIntoString(template1, map);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    //通过dataUrl获取静态化数据信息
    public Map getModel(String dataUrl) {
        if (dataUrl == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        if (body == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        return body;
    }

    //通过模板id获取模板
    public String getTemplate(String templateId) {
        if (templateId == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEIDISNULL);
        }
        Optional<CmsTemplate> op = cmsTemplateRespsitory.findById(templateId);
        if (!op.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        CmsTemplate cmsTemplate = op.get();
        String templateFileId = cmsTemplate.getTemplateFileId();
        String template = gridFsUtils.checkOut(templateFileId, null);
        return template;
    }

    //获取页面基本信息
    public CmsPage get(String pageId) {
        if (pageId == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CmsPageResult byId = findById(pageId);
        CmsPage cmsPage = byId.getCmsPage();
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }
        return cmsPage;
    }

    //保存页面到gridfs中 且发送消息到消息队列
    @Override
    public void post(String pageId) {
        //获取页面信息
        CmsPage cmsPage = get(pageId);
        //静态化
        String html = pageHtml(pageId);
        //1.上传文件
        String filedId = saveHtml(html, cmsPage.getPageName());
        //2.修改cmsPage的htmlfiled项
        cmsPage.setHtmlFileId(filedId);
        cmsPageRepository.save(cmsPage);
        //3.发送消息
        String message = "{pageId:'" + pageId + "'}";
        rabbitMqUtils.sendMessage(cmsPage.getSiteId(), message);
    }

    @Override
    public CmsPageResult save(CmsPage cmsPage) {
        CmsPage cmspage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmspage1 == null) {
            CmsPage save = cmsPageRepository.save(cmsPage);
            CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
            return cmsPageResult;
        } else {
            cmspage1.setTemplateId(cmsPage.getTemplateId());
            cmspage1.setDataUrl(cmsPage.getDataUrl());
            cmspage1.setPageName(cmsPage.getPageName());
            cmspage1.setPageAliase(cmsPage.getPageAliase());
            cmspage1.setPageWebPath(cmsPage.getPageWebPath());
            cmspage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            CmsPage save = cmsPageRepository.save(cmspage1);
            CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
            return cmsPageResult;
        }

    }

    //上传页面到gridfs
    public String saveHtml(String info, String fileName) {
        return gridFsUtils.insert(info, fileName);
    }
}
