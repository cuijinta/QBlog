package com.lut.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author 浅夜
 * @Description 文章详情响应对象
 * @DateTime 2023/10/26 11:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVO {
    private Long id;

    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //所属分类名称
    private String categoryName;
    //缩略图
    private String thumbnail;
    //内容
    private String content;
    //访问量
    private Long viewCount;

    private Date createTime;
}
