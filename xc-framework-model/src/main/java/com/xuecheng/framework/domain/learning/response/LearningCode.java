package com.xuecheng.framework.domain.learning.response;

import com.xuecheng.framework.model.response.ResultCode;

public enum LearningCode implements ResultCode {
    LEARNING_GETMEDIA_ERROR(false, 66666, "获取播放地址失败");

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    private LearningCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
