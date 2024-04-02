package com.lut.service.impl;

import com.google.gson.Gson;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.exception.GlobalException;
import com.lut.result.Result;
import com.lut.service.UploadService;
import com.lut.utils.AliOssUtils;
import com.lut.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 阿里oss文件上传业务层
 * @Author qianye
 * @Date 2024/3/20 23:43
 * @Version 1.0
 */
@Primary //默认实现（默认阿里云上传）
@Service
@Data
public class AliOssUploadServiceImpl implements UploadService {

    @Autowired
    private AliOssUtils aliOssUtils;

    /**
     * 图片上传
     *
     * @param img 图片实体
     * @return 上传结果
     */
    public Result uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        try {
            String originalFilename = img.getOriginalFilename();
            //对原始文件名进行判断
            if ((!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg"))
                    || !StringUtils.hasText(originalFilename)) {
                throw new GlobalException(AppHttpCodeEnum.FILE_TYPE_ERROR);
            }

            //如果判断通过上传文件到OSS
            String filePath = PathUtils.generateFilePath(originalFilename);
            String url = aliOssUtils.upload(img.getBytes(), filePath);
            return Result.okResult(url);
        } catch (IOException e) {
            throw new GlobalException(AppHttpCodeEnum.FILE_UPLOAD_ERROR);
        }
    }
}
