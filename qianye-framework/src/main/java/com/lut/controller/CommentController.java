package com.lut.controller;

import com.lut.pojo.entity.Comment;
import com.lut.result.Result;
import com.lut.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 浅夜
 * @Description 评论相关
 * @DateTime 2024/2/15 23:22
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public Result commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(articleId, pageNum, pageSize);
    }

    @PostMapping
    public Result addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }
}
