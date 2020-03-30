package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CoursePicResult extends ResponseResult {
    private String pic;

    public CoursePicResult(ResultCode resultCode, String pic) {
        super(resultCode);
        this.pic = pic;
    }

}
