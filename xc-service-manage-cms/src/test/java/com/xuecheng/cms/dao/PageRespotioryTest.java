package com.xuecheng.cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.ManageCmsApplication;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class PageRespotioryTest {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void findAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all.size());
        System.out.println(all);
    }

    @Test
    public void testPage() {
        Pageable pageable = PageRequest.of(1, 3);
        Page<CmsPage> pages = cmsPageRepository.findAll(pageable);
        System.out.println(pages.getSize());
        System.out.println(pages.getContent());
        System.out.println(pages.getTotalPages());
    }

    @Test
    public void testInsert() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("haha");
        cmsPage.setDataUrl("11..");
        cmsPage.setPageAliase("test00000");
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("5dada44dcb47ef4444f38c64");
    }

    @Test
    public void testUpdate() {
        Optional<CmsPage> optional = cmsPageRepository.findById("5dada4b0cb47ef027caae53e");
        System.out.println(optional);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            System.out.println(cmsPage);
            cmsPage.setPageName("bendan");
            cmsPageRepository.save(cmsPage);
        }
    }

    @Test
    public void testFindByPage() {
        CmsPage cmsPage = cmsPageRepository.findByPageName("bendan");
        System.out.println(cmsPage);
    }

    @Test
    public void testFindAll1() {
        Pageable page = PageRequest.of(1, 3);
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageWebPath("/cour");
        cmsPage.setPageAliase("课程");
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageWebPath", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        Page<CmsPage> all = cmsPageRepository.findAll(example, page);
        System.out.println(all);
    }
}
