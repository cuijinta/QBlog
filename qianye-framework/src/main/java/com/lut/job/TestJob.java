package com.lut.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description todo
 * @Author uht
 * @Date 2024/2/20 17:22
 * @Version 1.0
 */
@Component
public class TestJob {
//    @Scheduled(cron = "0/5 * * * * ?")
    public void testJob(){
        //要执行的代码
        System.out.println("定时任务执行了");
    }
}
