package com.lut.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author 浅夜
 * @Description 文章响应对象
 * @DateTime 2024/2/25 15:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO {
    private Long categoryId;
    private String content;
    private Date createTime;
    private Long id;
    private String isComment;
    private String isTop;
    private String status;
    private String summary;
    private String thumbnail;
    private String title;
    private Long viewCount;
    private List<Long> tags;
}
