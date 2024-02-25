package com.lut.controller;

import com.lut.result.Result;
import com.lut.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;

/**
 * @Author 浅夜
 * @Description 分类
 * @DateTime 2023/10/25 15:53
 **/
@RestController
@Api(tags = "文章分类")
@RequestMapping
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表 (前台只展示有文章的分类)
     *
     * @return
     */
    @GetMapping("/category/getCategoryList")
    public Result getCategoryList() {
        return categoryService.getCategoryList();
    }

    /**
     * 获取文章列表(后台需要列出所有分类)
     * @return
     */
    @GetMapping("/content/category/listAllCategory")
    public Result getAllCategory() {
        return categoryService.getAllCategory();
    }
}
