package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.OrderHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private OrderHystrixService orderHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = orderHystrixService.paymentInfo_OK(id);
        log.info("**************");
        return result;
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    /*@HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="4000")
    })*/
    @HystrixCommand
    public String paymentInfo_timeOut(@PathVariable("id") Integer id){
        String result = orderHystrixService.paymentInfo_timeOut(id);
        log.info("**************");
        return result;
    }

    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
        return "我是消费者，对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己/(ㄒoㄒ)/~~";
    }

    public String payment_Global_FallbackMethod(){
        return "Global处理异常信息，请稍后再试！";
    }

}
