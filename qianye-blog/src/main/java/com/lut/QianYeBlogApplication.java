package com.lut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lut.mapper")
public class QianYeBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(QianYeBlogApplication.class, args);
    }
}
