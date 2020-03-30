package com.xuecheng.api.auth.service;

import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.JwtResult;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.model.response.ResponseResult;

public interface AuthService {
    LoginResult login(LoginRequest loginRequest);

    ResponseResult logout(String jitKey);

    JwtResult getUserJwt(String uid);
}
