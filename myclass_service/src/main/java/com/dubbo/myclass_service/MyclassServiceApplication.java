package com.dubbo.myclass_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan("com.dubbo.myclass_service.service.impl")
public class MyclassServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyclassServiceApplication.class, args);
    }

}
