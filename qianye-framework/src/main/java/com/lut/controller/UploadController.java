package com.lut.controller;

import com.lut.result.Result;
import com.lut.service.UploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description 文件上传控制器
 * @Author qianye
 * @Date 2024/2/20 13:41
 * @Version 1.0
 */
@RestController
@RequestMapping
@Api(tags = "文件上传相关")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传
     * @param img 图片实体
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Result uploadImg(@RequestParam("img") MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
