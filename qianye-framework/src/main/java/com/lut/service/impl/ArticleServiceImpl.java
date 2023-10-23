package com.lut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.entity.Article;
import com.lut.mapper.ArticleMapper;
import com.lut.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-10-23 15:36:14
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
