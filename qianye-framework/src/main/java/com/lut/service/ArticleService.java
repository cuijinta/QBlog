package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.dto.ArticleDto;
import com.lut.pojo.entity.Article;
import com.lut.pojo.vo.ArticleDetailVO;
import com.lut.pojo.vo.ArticleVO;
import com.lut.pojo.vo.PageVO;
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

    /**
     * 分页获取文章
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param title 文章名 （文章名可模糊查询）
     * @param summary 文章摘要 （文章名可模糊查询）
     * @return 分页对象
     */
    Result<PageVO> pageArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    /**
     * 获取文章详情
     * @param id 文章id
     * @return 文章详情
     */
    Result<ArticleVO> getInfo(Long id);

    /**
     * 更新文章信息
     * @param articleDto 文章请求实体
     * @return
     */
    Result update(ArticleDto articleDto);

    /**
     * 根据id删除文章
     * @param id 文章id
     * @return
     */
    Result deleteById(Long id);
}
