package com.xuecheng.manage_course.service;

import com.xuecheng.api.course.service.CoursePicService;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CoursePicResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.respository.CoursePicRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CoursePicServiceImpl implements CoursePicService {
    @Autowired
    private CoursePicRepository coursePicRepository;

    @Override
    @Transactional
    public ResponseResult add(String courseId, String picId) {
        CoursePic coursePic1 = getCoursePic(courseId);
        if (coursePic1 != null) {
            coursePic1.setPic(picId);
            coursePicRepository.save(coursePic1);
        } else {
            CoursePic coursePic = new CoursePic();
            coursePic.setCourseid(courseId);
            coursePic.setPic(picId);
            coursePicRepository.save(coursePic);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public CoursePicResult getByCourseId(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CourseCode.COURSE_ID_COURSEIDISNULL);
        }
        CoursePic coursePic = getCoursePic(courseId);
        if (coursePic == null) {
            ExceptionCast.cast(CourseCode.COURSE_PICID_COURSEISNOTPIC);
        }
        return new CoursePicResult(CommonCode.SUCCESS, coursePic.getPic());
    }

    @Override
    public ResponseResult deleteById(String courseId) {
        coursePicRepository.deleteById(courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CoursePic getCoursePic(String courseId) {
        Optional<CoursePic> op = coursePicRepository.findById(courseId);
        if (op.isPresent()) {
            return op.get();
        }
        return null;
    }
}
