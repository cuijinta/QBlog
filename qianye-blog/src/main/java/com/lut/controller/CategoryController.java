package com.lut.controller;

import com.lut.result.Result;
import com.lut.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表
     *
     * @return
     */
    @GetMapping("/getCategoryList")
    public Result getCategoryList() {
        return categoryService.getCategoryList();
    }

}
