package com.xuecheng.auth.controller;

import com.xuecheng.api.auth.controller.AuthControllerApi;
import com.xuecheng.api.auth.service.AuthService;
import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.JwtResult;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AuthController implements AuthControllerApi {
    @Value("${auth.cookieDomain}")
    private String domain;
    @Value("${auth.cookieMaxAge}")
    private Integer cookieMaxAge;
    @Autowired
    private AuthService authService;

    @PostMapping("/userlogin")
    @Override
    public LoginResult login(LoginRequest loginRequest) {
        LoginResult login = authService.login(loginRequest);
        saveCookie(login.getToken());
        return login;
    }

    @GetMapping("/userlogout")
    @Override
    public ResponseResult logout() {
        String jitKey = getCookie();
        clareCookie(jitKey);
        return authService.logout(jitKey);
    }

    @GetMapping("/userjwt")
    @Override
    public JwtResult getUserJwt() {
        String uid = getCookie();
        JwtResult userJwt = authService.getUserJwt(uid);
        return userJwt;
    }

    private String getCookie() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = CookieUtil.readCookie(request, "uid");
        if (map != null && map.get("uid") != null) {
            return map.get("uid");
        }
        return null;
    }

    private void clareCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, domain, "/**", "uid", token, 0, false);
    }

    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, domain, "/", "uid", token, cookieMaxAge, false);
    }
}
