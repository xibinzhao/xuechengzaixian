package com.xuecheng.order.controller;

import com.xuecheng.api.order.controller.OrderControllerApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order/")
public class OrderController implements OrderControllerApi {
    @Override
    public void createOrder() {

    }
}
