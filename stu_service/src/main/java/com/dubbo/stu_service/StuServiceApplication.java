package com.dubbo.stu_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication

@DubboComponentScan("com.dubbo.stu_service.service.impl")
@EnableCaching
@EnableDubbo
public class StuServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StuServiceApplication.class, args);
    }

}
