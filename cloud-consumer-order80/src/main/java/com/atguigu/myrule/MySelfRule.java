package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        // 随机生成规则
        return new RandomRule();
    }
}
