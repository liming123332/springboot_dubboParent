package com.dubbo.myclass_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan("com.dubbo.myclass_service.service.impl")
@EnableDubbo
public class MyclassServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyclassServiceApplication.class, args);
    }

}
