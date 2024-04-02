package com.lut.config;

import com.lut.utils.AliOssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUtils对象
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Value("${aliOss.endpoint}")
    private String endpointValue;

    @Value("${aliOss.accessKeyId}")
    private String accessKeyIdValue;

    @Value("${aliOss.accessKeySecret}")
    private String accessKeySecretValue;

    @Value("${aliOss.bucketName}")
    private String bucketNameValue;

    @Bean
    @ConditionalOnMissingBean //当没有这个bean的时候创建
    public AliOssUtils aliOssUtil() {
//        log.info("开始创建阿里云文件上传工具类对象：{}", aliOssProperties);
        AliOssUtils aliOssUtil = new AliOssUtils(endpointValue, accessKeyIdValue,
                accessKeySecretValue, bucketNameValue);
        return aliOssUtil;
    }
}