package com.xuecheng.manage_course.service;

import com.github.pagehelper.PageHelper;
import com.xuecheng.api.course.service.CourseBaseService;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.mybatis.CourseBaseMapper;
import com.xuecheng.manage_course.dao.respository.CourseBaseRepository;
import com.xuecheng.manage_course.dao.respository.CourseMarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseBaseServiceImpl implements CourseBaseService {
    public static final Logger logger = LoggerFactory.getLogger(CourseBaseServiceImpl.class);
    @Autowired
    private CourseBaseMapper courseMapper;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CourseMarketRepository courseMarketRepository;

    @Override
    public CourseBase findById(String courseId) {
        if (courseId == null || courseId == "") {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseBase> op = courseBaseRepository.findById(courseId);
        if (!op.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        return op.get();
    }

    @Override
    public QueryResponseResult findAll(Integer page, Integer size, CourseListRequest courseListRequest) {
        if (courseListRequest == null) {
            logger.error("courseListRequest==null");
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String name = courseListRequest.getName();
        String companyId = courseListRequest.getCompanyId();
        if (companyId == null) {
            logger.error("companyId==null");
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        List<CourseBase> courses = null;
        Integer total = 0;
        if (name != null && name != "") {
            name = "%" + name + "%";
            PageHelper.startPage(page, size);
            courses = courseMapper.findByName(companyId, name);
            total = courseMapper.findByNameCount(companyId, name);
        } else {
            total = courseMapper.count(companyId);
            PageHelper.startPage(page, size);
            courses = courseMapper.findAll(companyId);
        }
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(total);
        queryResult.setList(courses);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    public AddCourseResult add(CourseBase courseBase) {
        if (courseBase == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        courseBase.setStatus("202001");
        CourseBase save = courseBaseRepository.save(courseBase);
        AddCourseResult addCourseResult = new AddCourseResult(CommonCode.SUCCESS, save.getId());
        return addCourseResult;
    }

    @Override
    public AddCourseResult update(CourseBase courseBase) {
        if (courseBase == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String id = courseBase.getId();
        CourseBase courseBase1 = findById(id);
        courseBase1.setDescription(courseBase.getDescription());
        courseBase1.setGrade(courseBase.getGrade());
        courseBase1.setMt(courseBase.getMt());
        courseBase1.setSt(courseBase.getSt());
        courseBase1.setStatus(courseBase.getStatus());
        courseBase1.setName(courseBase.getName());
        courseBase1.setStudymodel(courseBase.getStudymodel());
        courseBase1.setUserId(courseBase.getUserId());
        courseBase1.setUsers(courseBase.getUsers());
        courseBase1.setTeachmode(courseBase.getTeachmode());
        CourseBase save = courseBaseRepository.save(courseBase1);
        return new AddCourseResult(CommonCode.SUCCESS, save.getId());
    }

    @Override
    public CourseMarket getByCourseId(String courseId) {
        if (courseId == null || courseId == "") {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseMarket> op = courseMarketRepository.findById(courseId);
        if (!op.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        return op.get();
    }

    @Override
    public ResponseResult updateCourseMarket(CourseMarket courseMarket) {
        if (courseMarket == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<CourseMarket> op = courseMarketRepository.findById(courseMarket.getId());
        if (op.isPresent()) {
            CourseMarket courseMarket1 = op.get();
            courseMarket1.setCharge(courseMarket.getCharge());
            courseMarket1.setEndTime(courseMarket.getEndTime());
            courseMarket1.setStartTime(courseMarket.getStartTime());
            courseMarket1.setPrice(courseMarket.getPrice());
            courseMarket1.setPrice_old(courseMarket.getPrice_old());
            courseMarket1.setQq(courseMarket.getQq());
            courseMarket1.setValid(courseMarket.getValid());
            CourseMarket save = courseMarketRepository.save(courseMarket1);
        }
        courseMarketRepository.save(courseMarket);
        return new ResponseResult(CommonCode.SUCCESS);
    }


}
