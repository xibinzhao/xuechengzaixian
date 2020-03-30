package com.xuecheng.manage_course.dao.respository;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysDictionaryRepository extends MongoRepository<SysDictionary, String> {
    SysDictionary findByDType(String type);
}
