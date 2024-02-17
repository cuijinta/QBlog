package com.lut.pojo.vo;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @Author 浅夜
 * @Description 评论返回实体对象
 * @DateTime 2024/2/16 0:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
    private Long id;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复的目标评论的userName
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;

    private String username;

    private List<CommentVO> children;
}

