package com.lut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lut.pojo.entity.Article;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章表(Article)表数据库访问层
 *
 * @author qianye
 * @since 2023-10-23 15:33:43
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
