package com.lut.runner;

import com.lut.mapper.ArticleMapper;
import com.lut.pojo.entity.Article;
import com.lut.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 应用启动时初始化缓存
 * @Author qianye
 * @Date 2024/2/20 17:33
 * @Version 1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();
                }));
        //存储到redis中
        redisUtils.setCacheMap("article:viewCount",viewCountMap);
    }
}