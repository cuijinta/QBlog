package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * @return
     */
    Result hotArticleList();
}
