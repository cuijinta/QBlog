package com.lut;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.lut.mapper")
@EnableScheduling
@Slf4j
public class QianYeBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(QianYeBlogApplication.class, args);
        log.info("额滴博客~启动!");
    }
}
