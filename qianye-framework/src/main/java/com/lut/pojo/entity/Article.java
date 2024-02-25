package com.lut.pojo.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章表(Article)表实体类
 *
 * @author qianye
 * @since 2023-10-23 15:30:21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("qy_article")    //设置实体类与表名的映射
@Accessors(chain = true)   //set方法的返回值变成了属性值本身
public class Article implements Serializable {
    @TableId
    private Long id;

    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;

    @TableField(exist = false) //标识该字段不在表中，是后面添加的
    private String categoryName;

    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //这里不能自动更新，否则每5秒更新一次浏览数就会导致空指针
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //删除标志（0代表未删除，1代表已删除）
    @TableLogic
    private Integer delFlag;

    public Article(Long id, long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }

}
