package com.lut.pojo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 热门文章查询响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVO {
    private Long id;
    private String title; //文章标题
    private Long viewCount; //访问量
}
