package com.xuecheng.manage_cms_client.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import com.xuecheng.manage_cms_client.utils.GridFsUtils;

import com.xuecheng.manage_cms_client.utils.RabbitMqUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class PageLoderService {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Autowired
    private GridFsUtils gridFsUtils;
    @Autowired
    private RabbitMqUtils rabbitMqUtils;

    public void savePageToServerPath(String pageId) {
        //取出页面物理路径
        CmsPage cmsPage = getPage(pageId);
        String pagePath = cmsPage.getPagePhysicalPath() + "/" + cmsPage.getPageName();
        //查询页面且下载
        String htmlFileId = cmsPage.getHtmlFileId();
        InputStream inputStream = getFileById(htmlFileId);
        if (inputStream == null) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            System.out.println(pagePath);
            fileOutputStream = new FileOutputStream(pagePath);
            IOUtils.copy(inputStream, fileOutputStream);
            FileInputStream fileInputStream = new FileInputStream(pagePath);
            String html = IOUtils.toString(fileInputStream);
            System.out.println(html);

            rabbitMqUtils.sendMessage("client", "success", pageId);
        } catch (Exception e) {
            rabbitMqUtils.sendMessage("client", "exception", pageId);
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private CmsPage getPage(String pageId) {
        Optional<CmsPage> byId = cmsPageRepository.findById(pageId);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    public InputStream getFileById(String fileId) {
        return gridFsUtils.pull(fileId);
    }
}
