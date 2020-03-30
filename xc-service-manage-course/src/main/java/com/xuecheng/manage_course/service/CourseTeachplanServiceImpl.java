package com.xuecheng.manage_course.service;

import com.xuecheng.api.course.service.CourseTeachplanService;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.mybatis.CourseTeachplanMapper;
import com.xuecheng.manage_course.dao.respository.CourseTeachplanRepository;
import com.xuecheng.manage_course.dao.respository.TeachplanMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseTeachplanServiceImpl implements CourseTeachplanService {
    @Autowired
    private CourseTeachplanMapper courseTeachplanMapper;
    @Autowired
    private CourseTeachplanRepository courseTeachplanRepository;
    @Autowired
    private TeachplanMediaRepository teachplanMediaRepository;

    @Override
    public TeachplanNode list(String courseId) {
        return courseTeachplanMapper.findByCourseIdAndGrade(courseId);
    }

    @Override
    public ResponseResult add(Teachplan teachplan) {
        String parentid = teachplan.getParentid();
        Teachplan parent = null;
        if (parentid == null) {
            parent = courseTeachplanRepository.findByGradeAndCourseid("1", teachplan.getCourseid());
            if (parent == null) {
                Teachplan teachplan1 = new Teachplan();
                teachplan1.setGrade("1");
                teachplan1.setPname(teachplan.getPname());
                teachplan1.setTimelength(teachplan.getTimelength());
                teachplan1.setStatus(teachplan.getStatus());
                teachplan1.setCourseid(teachplan.getCourseid());
                teachplan1.setPtype(teachplan.getPtype());
                teachplan1.setOrderby(teachplan.getOrderby());
                teachplan1.setDescription(teachplan.getDescription());
                teachplan1.setParentid("0");
                teachplan1.setTrylearn(teachplan.getTrylearn());
                parent = courseTeachplanRepository.save(teachplan1);
            }
        } else {
            Optional<Teachplan> op = courseTeachplanRepository.findById(parentid);
            if (op.isPresent()) {
                parent = op.get();

            } else {
                ExceptionCast.cast(CommonCode.FAIL);
            }
        }
        Integer grade = Integer.valueOf(parent.getGrade()) + 1;
        teachplan.setParentid(parent.getId());
        teachplan.setGrade(grade + "");
        courseTeachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public ResponseResult saveMedia(TeachplanMedia teachplanMedia) {
        if (teachplanMedia == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        if (teachplanMedia.getTeachplanId() == null || teachplanMedia.getTeachplanId() == "") {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<Teachplan> op = courseTeachplanRepository.findById(teachplanMedia.getTeachplanId());
        int grade = 0;
        if (op.isPresent()) {
            Teachplan teachplan = op.get();
            grade = Integer.parseInt(teachplan.getGrade());
        }
        if (grade != 3) {
            ExceptionCast.cast(CourseCode.TEACHPLAN_GRADE_NOT3);
        } else {
            Optional<TeachplanMedia> op2 = teachplanMediaRepository.findById(teachplanMedia.getTeachplanId());
            TeachplanMedia teachplanMedia1 = null;
            if (op2.isPresent()) {
                teachplanMedia1 = op2.get();
            } else {
                teachplanMedia1 = new TeachplanMedia();
            }
            teachplanMedia1.setCourseId(teachplanMedia.getCourseId());
            teachplanMedia1.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
            teachplanMedia1.setMediaId(teachplanMedia.getMediaId());
            teachplanMedia1.setMediaUrl(teachplanMedia.getMediaUrl());
            teachplanMedia1.setTeachplanId(teachplanMedia.getTeachplanId());
            teachplanMediaRepository.save(teachplanMedia1);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
