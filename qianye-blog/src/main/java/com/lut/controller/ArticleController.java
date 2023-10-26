package com.lut.controller;

import com.lut.result.Result;
import com.lut.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询热门文章功能
     * @return
     */
    @GetMapping("/hotArticleList")
    public Result hotArticleList() {
        //查询热门文章
        Result result = articleService.hotArticleList();
        return result;
    }

    /**
     * 根据分类查询文章
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @GetMapping("/articleList")
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    /**
     * 获取文章详情的方法
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }
}
