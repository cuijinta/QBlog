package com.lut.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.entity.Category;
import com.lut.pojo.vo.PageVO;
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

    /**
     * 获取文章列表(后台需要列出所有分类)
     * @return
     */
    Result getAllCategory();

    Result<PageVO> pageCategoryList(Integer pageNum, Integer pageSize, String name, String status);

    Result delete(Long[] ids);
}
