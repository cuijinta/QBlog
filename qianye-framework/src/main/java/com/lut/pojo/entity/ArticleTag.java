package com.lut.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2024-02-25 14:49:42
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("qy_article_tag")
public class ArticleTag {
    @TableId
    private Long id;
    //文章id
    private Long articleId;
    //标签id
//    @TableId
    private Long tagId;


}
