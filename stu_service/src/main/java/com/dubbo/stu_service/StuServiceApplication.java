package com.dubbo.stu_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@DubboComponentScan("com.dubbo.stu_service.service.impl")
public class StuServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StuServiceApplication.class, args);
    }

}
