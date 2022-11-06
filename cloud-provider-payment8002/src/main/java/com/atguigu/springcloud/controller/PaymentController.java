package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult<Payment> createPayment(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if(result > 0){
            return new CommonResult(200,"创建成功,端口号为："+serverPort,result);
        }else{
            return new CommonResult(404,"创建失败,端口号为："+serverPort,result);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("************************");
        if(result != null){
            return new CommonResult(200,"查询成功,端口号为"+serverPort,result);
        }else{
            return new CommonResult(404,"查询失败,端口号为"+serverPort,null);
        }
    }

    @GetMapping("/payment/lb")
    public String paymentLb(){
        return serverPort;
    }
}
