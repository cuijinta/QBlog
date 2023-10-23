package com.lut.controller;

import com.lut.entity.Article;
import com.lut.result.Result;
import com.lut.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @ApiOperation("热门文章")
    public Result hotArticleList() {
        //查询热门文章
        Result result = articleService.hotArticleList();
        return result;
    }
}
