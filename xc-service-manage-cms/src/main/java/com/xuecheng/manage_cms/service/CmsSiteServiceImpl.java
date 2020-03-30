package com.xuecheng.manage_cms.service;

import com.xuecheng.api.cms.service.CmsSiteService;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsSiteServiceImpl implements CmsSiteService {
    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Override
    public QueryResponseResult findAll() {
        QueryResult<CmsSite> cmsSiteQueryResult = new QueryResult<>();
        List<CmsSite> all = cmsSiteRepository.findAll();
        cmsSiteQueryResult.setList(all);
        cmsSiteQueryResult.setTotal(all.size());
        return new QueryResponseResult(CommonCode.SUCCESS, cmsSiteQueryResult);
    }
}
