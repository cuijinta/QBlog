package com.lut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lut.pojo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-21 11:14:47
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    
}

