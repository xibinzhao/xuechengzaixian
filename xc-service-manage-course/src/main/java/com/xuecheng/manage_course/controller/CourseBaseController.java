package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.controller.CourseBaseControllerApi;
import com.xuecheng.api.course.service.CourseBaseService;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.utils.XcOauth2Util;
import com.xuecheng.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/coursebase")
public class CourseBaseController extends BaseController implements CourseBaseControllerApi {
    @Autowired
    private CourseBaseService courseService;

    @PreAuthorize("hasAuthority('xc_teachmanager_course_base')")
    @GetMapping("/getById/{id}")
    @Override
    public CourseBase findById(@PathVariable("id") String courseId) {
        return courseService.findById(courseId);
    }

    @PreAuthorize("hasAuthority('course_find_list')")
    @PostMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestBody CourseListRequest courseListRequest) {
        XcOauth2Util xcOauth2Util = new XcOauth2Util();
        XcOauth2Util.UserJwt userJwtFromHeader = xcOauth2Util.getUserJwtFromHeader(request);
        courseListRequest.setCompanyId(userJwtFromHeader.getCompanyId());
        QueryResponseResult all = courseService.findAll(page, size, courseListRequest);
        return all;
    }

    @PreAuthorize("hasAuthority('xc_teachmanager_course_add')")
    @PostMapping("add")
    @Override
    public AddCourseResult add(@RequestBody CourseBase courseBase) {
        return courseService.add(courseBase);
    }

    @PreAuthorize("hasAuthority('xc_teachmanager_course_update')")
    @PostMapping("update")
    @Override
    public AddCourseResult update(@RequestBody CourseBase courseBase) {
        return courseService.update(courseBase);
    }

    @PreAuthorize("hasAuthority('xc_teachmanager_course_market')")
    @GetMapping("/getByCourseId/{id}")
    @Override
    public CourseMarket getByCourseId(@PathVariable("id") String courseId) {
        return courseService.getByCourseId(courseId);
    }

    @PreAuthorize("hasAuthority('xc_teachmanager_course_updateCourseMarket')")
    @PostMapping("/updateCourseMarket")
    @Override
    public ResponseResult updateCourseMarket(@RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(courseMarket);
    }
}
