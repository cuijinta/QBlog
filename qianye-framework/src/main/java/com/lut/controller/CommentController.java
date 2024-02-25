package com.lut.controller;

import com.lut.constant.SystemConstants;
import com.lut.pojo.entity.Comment;
import com.lut.result.Result;
import com.lut.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 浅夜
 * @Description 评论相关
 * @DateTime 2024/2/15 23:22
 **/
@RestController
@RequestMapping("/comment")
@Api(tags="评论相关")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 分页获取文章评论
     *
     * @param articleId 文章id
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @return 分页对象
     */
    @GetMapping("/commentList")
    public Result commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    /**
     * 发评论
     * @param comment 评论实体
     * @return 发送成功
     */
    @PostMapping
    public Result addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    /**
     * 新增标签
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/linkCommentList")
    public Result linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
