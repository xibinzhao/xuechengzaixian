package com.xuecheng.api.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("处理订单服务的接口")
public interface OrderControllerApi {
    @ApiOperation("创建订单")
    public void createOrder();
}
