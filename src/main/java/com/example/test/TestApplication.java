package com.example.test;

import org.apache.log4j.BasicConfigurator;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.test.mapper")
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
//        BasicConfigurator.configure();

        SpringApplication.run(TestApplication.class, args);
    }

}
