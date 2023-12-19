package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.SystemConstants;
import com.lut.pojo.entity.Article;
import com.lut.mapper.ArticleMapper;
import com.lut.pojo.entity.Category;
import com.lut.pojo.vo.ArticleDetailVO;
import com.lut.pojo.vo.ArticleListVO;
import com.lut.pojo.vo.HotArticleVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.ArticleService;
import com.lut.service.CategoryService;
import com.lut.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-10-23 15:36:14
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取热门文章列表的方法
     *
     * @return
     */
    public Result hotArticleList() {
        //查询热门文章 封装成Result封装返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是已发布的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);

        //最多查询 10 条
        Page<Article> pages = new Page(1, 10);
        page(pages, queryWrapper);

        List<Article> articles = pages.getRecords();

        //属性拷贝
        List<HotArticleVO> hotArticleVOS = BeanCopyUtils.copyBeanList(articles, HotArticleVO.class);

        return Result.okResult(hotArticleVOS);
    }

    /**
     * 根据分类查询文章的方法
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //1.如果有category 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //文章的状态是已经发布的
        //对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        //写法1：
//        //查询分类名称
//        List<Article> articleList = page.getRecords();
//        for(Article article : articleList) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //写法2：
//        List<Article> articleList = page.getRecords();
//        articleList = articleList.stream()
//                .map(new Function<Article, Article>() {
//                    public Article apply(Article article) {
//                        return article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
//                    }
//                }).collect(Collectors.toList());

        //最简化写法
        List<Article> articleList = page.getRecords();
        articleList = articleList.stream()
                .map(article ->  article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVO> articleListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVO.class);
        PageVO pageVO = new PageVO(articleListVOS, page.getTotal());
        return Result.okResult(pageVO);
    }

    /**
     * 根据文章id获取详情
     * @param id
     * @return
     */
    public Result getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);

        //转换成VO
        ArticleDetailVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVO.class);

        //查询对应的分类名称(先从拷贝后的vo当中拿出categoryId,再根据Id查询分类名称)
        Long categoryId = articleDetailVO.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category != null) {
            articleDetailVO.setCategoryName(category.getName());
        }

        //封装响应返回
        return Result.okResult(articleDetailVO);
    }

}
