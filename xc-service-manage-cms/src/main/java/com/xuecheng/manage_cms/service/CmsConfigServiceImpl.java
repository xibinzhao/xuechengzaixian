package com.xuecheng.manage_cms.service;

import com.xuecheng.api.cms.service.CmsConfigService;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CmsConfigServiceImpl implements CmsConfigService {
    @Autowired
    private CmsConfigRepository cmsConfigRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CmsConfig findById(String id) {
        Optional<CmsConfig> op = cmsConfigRepository.findById(id);
        if (op.isPresent()) {
            return op.get();
        }
        return null;
    }
}
