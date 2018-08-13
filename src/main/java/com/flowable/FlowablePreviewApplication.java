package com.flowable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.flowable.modules.*.mapping")
public class FlowablePreviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowablePreviewApplication.class, args);
    }
}
