package com.xuecheng.ucenter.controller;

import com.xuecheng.api.ucenter.controller.XcUserControllerApi;
import com.xuecheng.api.ucenter.service.XcUserService;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ucenter")
public class XcUserController implements XcUserControllerApi {
    @Autowired
    private XcUserService xcUserService;

    @GetMapping("/getuserext")
    @Override
    public XcUserExt getUserext(String username) {
        return xcUserService.getUserext(username);
    }
}
