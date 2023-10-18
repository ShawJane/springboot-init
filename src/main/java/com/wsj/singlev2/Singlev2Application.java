package com.wsj.singlev2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wsj.singlev2.mapper")
@SpringBootApplication
public class Singlev2Application {

    public static void main(String[] args) {
        SpringApplication.run(Singlev2Application.class, args);
    }

}
