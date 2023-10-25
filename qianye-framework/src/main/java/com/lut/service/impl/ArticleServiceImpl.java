package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.SystemConstants;
import com.lut.pojo.entity.Article;
import com.lut.mapper.ArticleMapper;
import com.lut.pojo.entity.vo.HotArticleVO;
import com.lut.result.Result;
import com.lut.service.ArticleService;
import com.lut.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
