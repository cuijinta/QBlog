package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.dto.ArticleDto;
import com.lut.pojo.entity.Article;
import com.lut.result.Result;


/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-10-23 15:34:54
 */
public interface ArticleService extends IService<Article> {

    /**
     * 获取热门文章列表的方法
     *
     * @return
     */
    Result hotArticleList();

    /**
     * 根据分类id查询文章的方法
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    Result articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 根据文章id获取详情
     *
     * @param id
     * @return
     */
    Result getArticleDetail(Long id);

    /**
     * 更新浏览次数
     *
     * @param id 文章id
     * @return 浏览次数
     */
    Result updateViewCount(Long id);

    /**
     * 写文章
     * @param article 文章请求对象
     * @return
     */
    Result add(ArticleDto article);
}
