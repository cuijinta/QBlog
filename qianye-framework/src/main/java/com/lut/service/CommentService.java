package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.entity.Comment;
import com.lut.result.Result;

/**
 * 评论表(Comment)表服务接口
 *
 * @author qianye
 * @since 2024-02-15 23:49:14
 */
public interface CommentService extends IService<Comment> {

    /**
     * 分页获取评论列表
     *
     * @param commentType 评论类型
     * @param articleId 评论所属文章id
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @return 分页列表
     */
    Result commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 发送评论
     * @param comment 评论对象
     * @return 发送成功
     */
    Result addComment(Comment comment);
}
