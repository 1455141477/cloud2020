package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult<Payment> createPayment(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if(result > 0){
            return new CommonResult(200,"创建成功，端口号为"+serverPort,result);
        }else{
            return new CommonResult(404,"创建失败,端口号为"+serverPort,result);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("************************");
        if(result != null){
            return new CommonResult(200,"查询成功,端口号为："+serverPort,result);
        }else{
            return new CommonResult(404,"查询失败,端口号为："+serverPort,null);
        }
    }

    @GetMapping(value="/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service:services){
            System.out.println("************"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            System.out.println("serviceId:"+instance.getServiceId()+" host:"+instance.getHost()+" port:"+instance.getPort()+" uri:"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/payment/lb")
    public String paymentLb(){
        return serverPort;
    }
}
