package com.lut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lut.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author qianye
 * @since 2023-10-25 15:18:30
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
