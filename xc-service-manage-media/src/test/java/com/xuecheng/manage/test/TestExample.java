package com.xuecheng.manage.test;

import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.manage_media.ManageMediaApplication;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = ManageMediaApplication.class)
@RunWith(SpringRunner.class)
public class TestExample {
    @Autowired
    private MediaFileRepository repository;

    @Test
    public void test() {
        Example<MediaFile> example = null;
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileOriginalName("avi");
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().
                withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains()).
                withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains());
        example = Example.of(mediaFile, exampleMatcher);
        List<MediaFile> all = repository.findAll(example);
        System.out.println(all);
    }
}
