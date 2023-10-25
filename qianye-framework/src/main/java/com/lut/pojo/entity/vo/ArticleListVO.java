package com.lut.pojo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author 浅夜
 * @Description 文章列表响应对象
 * @DateTime 2023/10/25 17:49
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVO {
    private Long id;

    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名称
    private String categoryName;
    //缩略图
    private String thumbnail;

    private Long viewCount;

    private Date createTime;
}
