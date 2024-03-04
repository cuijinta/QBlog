package com.lut.controller;

import com.lut.pojo.dto.ArticleDto;
import com.lut.pojo.vo.ArticleDetailVO;
import com.lut.pojo.vo.ArticleVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "文章相关")
@RequestMapping
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询热门文章功能
     * @return
     */
    @GetMapping("/article/hotArticleList")
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
    @GetMapping("/article/articleList")
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    /**
     * 获取文章详情的方法
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public Result getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }


    /**
     * 更新浏览次数
     *
     * @param id 文章id
     * @return 浏览次数
     */
    @PutMapping("/article/updateViewCount/{id}")
    public Result updateViewCount(@PathVariable("id") Long id) {
        return articleService.updateViewCount(id);
    }

    /**
     * 写文章
     * @param article 文章请求对象
     * @return
     */
    @PostMapping("/content/article")
    public Result add(@RequestBody ArticleDto article) {
        return articleService.add(article);
    }

    /**
     * 分页获取文章
     *
     * @param pageNum  当前页数
     * @param pageSize 每页条数
     * @param title    文章名 （文章名可模糊查询）
     * @param summary  文章摘要 （文章名可模糊查询）
     * @return 分页对象
     */
    @GetMapping("/content/article/list")
    public Result<PageVO> list(Integer pageNum, Integer pageSize, String title, String summary) {
        return articleService.pageArticleList(pageNum, pageSize, title, summary);
    }

    /**
     * 获取文章详情
     * @param id 文章id
     * @return 文章详情
     */
    @GetMapping("content/article/{id}")
    public Result<ArticleVO> info(@PathVariable Long id) {
        return articleService.getInfo(id);
    }

    /**
     * 更新文章信息
     * @param articleDto 文章请求实体
     * @return
     */
    @PutMapping("content/article")
    public Result update(@RequestBody ArticleDto articleDto) {
        return articleService.update(articleDto);
    }

    @DeleteMapping("content/article/{id}")
    public Result delete(@PathVariable Long id) {
        return articleService.deleteById(id);
    }
}
