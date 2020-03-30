package com.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.cms.service.CmsPageService;
import com.xuecheng.api.course.service.CourseBaseService;
import com.xuecheng.api.course.service.CoursePerviewService;
import com.xuecheng.api.course.service.CoursePicService;
import com.xuecheng.api.course.service.CourseTeachplanService;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.*;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.dao.respository.CourseBaseRepository;
import com.xuecheng.manage_course.dao.respository.CoursePubRepository;
import com.xuecheng.manage_course.dao.respository.TeachplanMediaPubRepository;
import com.xuecheng.manage_course.dao.respository.TeachplanMediaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CoursePerviewServiceImpl implements CoursePerviewService {
    @Autowired
    private CourseBaseService courseBaseService;
    @Autowired
    private CoursePicService coursePicService;
    @Autowired
    private CourseTeachplanService courseTeachplanService;
    @Autowired
    private CmsPageClient cmsPageClient;
    @Autowired
    private CoursePubRepository coursePubRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private TeachplanMediaPubRepository teachplanMediaPubRepository;
    @Autowired
    private TeachplanMediaRepository teachplanMediaRepository;

    @Override
    public CourseView getById(String courseId) {
        CourseMarket courseMarket = courseBaseService.getByCourseId(courseId);
        CoursePic coursePic = coursePicService.getCoursePic(courseId);
        TeachplanNode teachplanNode = courseTeachplanService.list(courseId);
        CourseBase courseBase = courseBaseService.findById(courseId);
        CourseView courseView = new CourseView();
        courseView.setCourseBase(courseBase);
        courseView.setCourseMarket(courseMarket);
        courseView.setTeachplanNode(teachplanNode);
        courseView.setCoursePic(coursePic);
        return courseView;
    }

    @Override
    public CmsPage perview(String courseId) {
        CmsPageResult cmsPageResult = addCmsPage(courseId);
        return cmsPageResult.getCmsPage();
    }

    @Override
    public CmsPageResult publish(String courseId) {
        CmsPageResult cmsPageResult = addCmsPage(courseId);
        if (cmsPageResult == null) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_CDETAILERROR);
        }
        if (cmsPageResult.getCmsPage() == null) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_CDETAILERROR);
        }
        try {
            CoursePub coursePub = create(courseId);
            savaCoursePub(coursePub);
            saveTeachMediaPub(courseId);
            cmsPageClient.post(cmsPageResult.getCmsPage().getPageId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CmsPageResult(CommonCode.SUCCESS, cmsPageResult.getCmsPage());
    }

    @Transactional
    public void saveTeachMediaPub(String courseId) {
        teachplanMediaPubRepository.deleteByCourseId(courseId);
        List<TeachplanMedia> list = teachplanMediaRepository.findByCourseId(courseId);
        ArrayList<TeachplanMediaPub> teachplanMediaPubs = new ArrayList<>();
        for (TeachplanMedia teachplanMedia : list) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia, teachplanMediaPub);
            teachplanMediaPub.setTimestamp(new Date());
            teachplanMediaPubs.add(teachplanMediaPub);
        }
        teachplanMediaPubRepository.saveAll(teachplanMediaPubs);
    }

    public CoursePub create(String courseId) {
        CoursePub coursePub = new CoursePub();
        //获取课程基本信息
        CourseBase courseBase = courseBaseService.findById(courseId);
        BeanUtils.copyProperties(courseBase, coursePub);
        //获取课程计划
        TeachplanNode list = courseTeachplanService.list(courseId);
        String s = JSON.toJSONString(list);
        coursePub.setTeachplan(s);
        //获取课程价格
        CourseMarket courseMarket = courseBaseService.getByCourseId(courseId);
        BeanUtils.copyProperties(courseMarket, coursePub);
        //获取课程图片
        CoursePic coursePic = coursePicService.getCoursePic(courseId);
        BeanUtils.copyProperties(coursePic, coursePub);
        coursePub.setId(courseId);
        return coursePub;
    }

    public CoursePub savaCoursePub(CoursePub coursePub) {
        CoursePub coursePub1 = null;
        Optional<CoursePub> byId = coursePubRepository.findById(coursePub.getId());
        if (byId.isPresent()) {
            coursePub1 = byId.get();
        } else {
            coursePub1 = new CoursePub();
        }
        BeanUtils.copyProperties(coursePub, coursePub1);
        coursePub1.setTimestamp(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY‐MM‐dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePub1.setPubTime(date);
        CoursePub save = coursePubRepository.save(coursePub1);
        return save;
    }

    public CmsPageResult addCmsPage(String courseId) {
        CourseBase course = courseBaseService.findById(courseId);
        if (course == null) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
        }
        course.setStatus("202002");
        CmsPage cmsPage = new CmsPage();
        cmsPage.setDataUrl("http://localhost:31200/course/preview/getModel/" + courseId);
        cmsPage.setPageName(courseId + ".html");
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setTemplateId("5dc38c35cb47ef68e0e6e927");
        cmsPage.setPageWebPath("/course/");
        cmsPage.setPageAliase(course.getName());
        cmsPage.setPageCreateTime(new Date());
        cmsPage.setPagePhysicalPath("E:/xc_html/static/course/detail");
        cmsPage.setPageType("0");
        courseBaseRepository.save(course);
        CmsPageResult add = cmsPageClient.save(cmsPage);
        return add;
    }

}
