package com.lut.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.entity.Category;
import com.lut.result.Result;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-10-25 15:18:32
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取分类列表
     * @return
     */
    Result getCategoryList();
}
