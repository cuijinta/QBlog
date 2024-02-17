package com.lut.pojo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2024-02-15 23:49:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("qy_comment")
public class Comment {
    @TableId
    private Long id;

    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;

    @TableField(fill = FieldFill.INSERT) //表示在插入时填充
    private Long createBy;

    @TableField(fill = FieldFill.INSERT) //表示在插入时填充
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE) //插入或更新时填充
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE) //插入或更新时填充
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @TableLogic
    private Integer delFlag;


}
