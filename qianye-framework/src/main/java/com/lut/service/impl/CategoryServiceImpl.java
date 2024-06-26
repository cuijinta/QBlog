package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.constant.SystemConstants;
import com.lut.mapper.CategoryMapper;
import com.lut.pojo.entity.Article;
import com.lut.pojo.entity.Category;
import com.lut.pojo.vo.CategoryVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.ArticleService;
import com.lut.service.CategoryService;
import com.lut.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-10-25 15:18:33
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 获取分类列表
     * @return
     */
    public Result getCategoryList() {

        //分步查询： 1，查询文章表中已发布的文章，并且去重
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(lambdaQueryWrapper);

        //2.获取文章的分类id，并且去重
        Set<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        //3.查询分类表
        List<Category> categories = listByIds(categoryIds);

        categories = categories.stream().filter(category -> category.getStatus().equals(SystemConstants.CATEGORY_STATUS_NORMAL))
                .collect(Collectors.toList());

       //4.封装VO
        List<CategoryVO> categoryVOList = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);
        return Result.okResult(categoryVOList);
    }

    /**
     * 获取文章列表(后台需要列出所有分类)
     * @return
     */
    @Override
    public Result getAllCategory() {
        List<Category> categoryList = list();
        if(!CollectionUtils.isEmpty(categoryList)) {
            List<CategoryVO> categoryVOs = BeanCopyUtils.copyBeanList(categoryList, CategoryVO.class);
            return Result.okResult(categoryVOs);
        }
        return Result.okResult();
    }

    @Override
    public Result<PageVO> pageCategoryList(Integer pageNum, Integer pageSize, String name, String status) {
        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name), Category::getName, name);
        queryWrapper.eq(StringUtils.hasText(status), Category::getStatus, status);
        page(page, queryWrapper);

        return Result.okResult(new PageVO(page.getRecords(), page.getTotal()));
    }

    @Override
    public Result delete(Long[] ids) {
        if(ids.length == 0) return Result.errorResult(AppHttpCodeEnum.OPERATION_ERROR);
        List<Long> idList = (List<Long>) CollectionUtils.arrayToList(ids);
        categoryMapper.deleteBatchIds(idList);
        idList.forEach(cateId -> {
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getCategoryId, cateId);
            articleService.remove(queryWrapper);
        });
        return Result.okResult();
    }
}
