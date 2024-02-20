package com.lut.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Description todo
 * @Author uht
 * @Date 2024/2/20 17:17
 * @Version 1.0
 */
@Component
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("程序初始化");
    }
}
