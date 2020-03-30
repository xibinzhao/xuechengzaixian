package com.xuecheng.manage_course.service;

import com.xuecheng.api.sys.service.SysDictionaryService;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.dao.respository.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {
    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    @Override
    public SysDictionary get(String type) {
        return sysDictionaryRepository.findByDType(type);
    }
}
