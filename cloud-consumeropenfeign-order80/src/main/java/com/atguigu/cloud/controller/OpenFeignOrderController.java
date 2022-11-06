package com.atguigu.cloud.controller;

import com.atguigu.cloud.service.OpenFeignOrderService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OpenFeignOrderController {

    @Resource
    private OpenFeignOrderService openFeignOrderService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return openFeignOrderService.getPaymentById(id);
    }
}
