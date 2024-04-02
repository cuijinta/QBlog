package com.lut.service.impl;

import com.google.gson.Gson;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.exception.GlobalException;
import com.lut.result.Result;
import com.lut.service.UploadService;
import com.lut.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Description 文件上传业务层
 * @Author qianye
 * @Date 2024/2/20 13:43
 * @Version 1.0
 */
@Service
@Data
//@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;

    /**
     * 图片上传
     *
     * @param img 图片实体
     * @return 上传结果
     */
    public Result uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if ((!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg"))
                || !StringUtils.hasText(originalFilename)) {
            throw new GlobalException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        //如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        return Result.okResult(url);
    }

    private String uploadOss(MultipartFile imgFile, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
//                return "http://r7yxkqloa.bkt.clouddn.com/" + key;
                return "http://s94tz8lzk.hd-bkt.clouddn.com/" + key; //http://s94tz8lzk.hd-bkt.clouddn.com/2024/qianye.png
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "www";
    }
}
