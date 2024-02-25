package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.constant.SystemConstants;
import com.lut.pojo.dto.ArticleDto;
import com.lut.pojo.entity.Article;
import com.lut.mapper.ArticleMapper;
import com.lut.pojo.entity.ArticleTag;
import com.lut.pojo.entity.Category;
import com.lut.pojo.vo.*;
import com.lut.result.Result;
import com.lut.service.ArticleService;
import com.lut.service.ArticleTagService;
import com.lut.service.CategoryService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.RedisUtils;
import com.lut.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author qianye
 * @since 2023-10-23 15:36:14
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ArticleTagService articleTagService;

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
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVO> articleListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVO.class);
        PageVO pageVO = new PageVO(articleListVOS, page.getTotal());
        return Result.okResult(pageVO);
    }

    /**
     * 根据文章id获取详情
     *
     * @param id
     * @return
     */
    public Result getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);

        //从redis中获取viewCount
        Integer viewCount = redisUtils.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());

        //转换成VO
        ArticleDetailVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVO.class);

        //查询对应的分类名称(先从拷贝后的vo当中拿出categoryId,再根据Id查询分类名称)
        Long categoryId = articleDetailVO.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVO.setCategoryName(category.getName());
        }

        //封装响应返回
        return Result.okResult(articleDetailVO);
    }

    /**
     * 更新浏览次数
     *
     * @param id 文章id
     * @return 浏览次数
     */
    @Override
    public Result updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisUtils.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return Result.okResult();
    }

    /**
     * 写文章
     * @param articleDto 文章请求对象
     * @return
     */
    @Override
    @Transactional
    public Result add(ArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        articleMapper.insert(article);

        List<Long> tagIds = articleDto.getTags();
        if(CollectionUtils.isEmpty(tagIds)) return Result.okResult();

        //添加 博客和标签的关联
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", article.getId());
        //先清空
        articleTagService.remove(queryWrapper);
        tagIds.forEach(tagId -> {
            articleTagService.save(new ArticleTag(null, article.getId(), tagId));
        });
        return Result.okResult();
    }

    /**
     * 分页获取文章
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param title 文章名 （文章名可模糊查询）
     * @param summary 文章摘要 （文章名可模糊查询）
     * @return 分页对象
     */
    @Override
    public Result<PageVO> pageArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasText(title), Article::getTitle, title);
        lambdaQueryWrapper.like(StringUtils.hasText(summary), Article::getTitle, summary);
//        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Page<Article> page1 = page(page, lambdaQueryWrapper);
        List<Article> articleList = page1.getRecords();
        if(ObjectUtils.isEmpty(articleList)) return Result.okResult();
        //封装数据返回
        List<ArticleVO> articleVOS = BeanCopyUtils.copyBeanList(articleList, ArticleVO.class);
        PageVO pageVO = new PageVO(articleVOS, page1.getTotal());
        return Result.okResult(pageVO);
    }

    /**
     * 获取文章详情
     * @param id 文章id
     * @return 文章详情
     */
    @Override
    public Result<ArticleVO> getInfo(Long id) {
        Article article = getById(id);
        ArticleVO articleVO = BeanCopyUtils.copyBean(article, ArticleVO.class);
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", article.getId());
        List<ArticleTag> articleTagList = articleTagService.list(queryWrapper);
        if(CollectionUtils.isEmpty(articleTagList)) return Result.okResult(articleVO);

        List<Long> tagList = articleTagList.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        articleVO.setTags(tagList);
        return Result.okResult(articleVO);
    }

    /**
     * 更新文章
     * @param articleDto 文章请求实体
     * @return
     */
    @Override
    @Transactional
    public Result update(ArticleDto articleDto) {
        Long userId = SecurityUtils.getUserId();
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        article.setUpdateBy(userId);
        article.setUpdateTime(new Date());

        List<Long> tags = articleDto.getTags();
        //提前删除，不然取消标签之后如果tags为空就删不掉
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", article.getId());
        //这种方法不行，如果更新的标签变少了，表中的记录并没有减少
//                List<ArticleTag> list = articleTagService.list(queryWrapper);
//                if(!list.isEmpty()) return;
        //应该先将这个文章的标签清除，然后再添加要更新的标签
        articleTagService.remove(queryWrapper);
        if(!CollectionUtils.isEmpty(tags)) {
            tags.forEach(tag -> {
                articleTagService.save(new ArticleTag(null, article.getId(), tag));
            });
        }
        QueryWrapper<Article> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", article.getId());
        articleMapper.update(article, queryWrapper1);
        return Result.okResult();
    }

    @Override
    public Result deleteById(Long id) {
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", id);
        articleTagService.remove(queryWrapper);

        int update = articleMapper.deleteById(id);
        return update > 0 ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

}
